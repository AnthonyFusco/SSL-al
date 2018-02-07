package kernel.visitor;

import kernel.Application;
import kernel.Measurement;
import kernel.structural.Sensor;
import kernel.structural.SensorsLot;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;
import org.influxdb.dto.Query;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class SslVisitor implements Visitor {

    @Override
    public void visit(Application application) {
        for (SensorsLot sensorsLot : application.getSensorsLots()) {
            visitSensorsLot(sensorsLot, application.getStartDate(), application.getEndDate());
        }
    }

    private void visitSensorsLot(SensorsLot lot, Date startDate, Date endDate) {
        double period = 1.0 / lot.getStepFrequency();
        for (double t = startDate.getTime(); t < endDate.getTime(); t += period) {
            List<Measurement> measurements = new ArrayList<>();
            for (Sensor sensor : lot.getSensors()) {
                Measurement measurement = sensor.generateNextMeasurement(t);
                if (measurement == null) {
                    continue; //todo no value in csv / handle the case ?
                }
                measurements.add(measurement);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            sendToInfluxDB(measurements);
        }
    }

    //todo gerer les differents noms possible
    private void sendToInfluxDB(List<Measurement> measurements) {
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
        String dbName = "influxdb";
        influxDB.createDatabase(dbName);

        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();

        for (Measurement measurement : measurements) {

            Map<String, Object> map = new HashMap<>();
            map.put(measurement.getSensorName(), measurement.getValue());

            Point point = Point.measurement(measurement.getSensorName())
                    .time(measurement.getTimeStamp(), TimeUnit.MILLISECONDS)
                    .fields(map)
                    .build();
            batchPoints.point(point);
        }

//        System.out.println(batchPoints);
        influxDB.write(batchPoints);
//        Query query = new Query("SELECT * FROM " + measurements.get(0).getSensorName(), dbName);
//        System.out.println(influxDB.query(query));
    }

}
