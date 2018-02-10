package kernel.visitor;

import kernel.Application;
import kernel.Measurement;
import kernel.structural.Sensor;
import kernel.structural.SensorsLot;
import kernel.structural.replay.Replay;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class SslVisitor implements Visitor {

    @Override
    public void visit(Application application) {
        for (SensorsLot sensorsLot : application.getSensorsLots()) {
            visitSensorsLot(sensorsLot, application.getStartDate(), application.getEndDate());
        }

        for (Replay replay : application.getReplays()) {
            visitReplay(replay);
        }
    }

    private void visitReplay(Replay replay) {
        sendToInfluxDB(replay.getMeasurements(), replay.getName(), "Replay");
    }

    private void visitSensorsLot(SensorsLot lot, Date startDate, Date endDate) {
        double period = 1.0 / lot.getFrequencyValue();
        for (double t = startDate.getTime(); t < endDate.getTime(); t += period) {
            List<Measurement> measurements = new ArrayList<>();
            for (Sensor sensor : lot.getSensors()) {
                Measurement measurement = sensor.generateNextMeasurement(t);
                if (measurement == null) {
                    continue; //todo no value in csv / handle the case ?
                }
                measurements.add(measurement);
            }
            sendToInfluxDB(measurements, lot.getName(), lot.getLawName());
        }
    }

    private void sendToInfluxDB(List<Measurement> measurements, String sensorLotName, String lawName) {
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root");
        String dbName = "influxdb";
        influxDB.createDatabase(dbName);

        BatchPoints batchPoints = BatchPoints
                .database(dbName)
                .consistency(InfluxDB.ConsistencyLevel.ALL)
                .build();

        for (Measurement measurement : measurements) {

            Map<String, Object> map = new HashMap<>();
            map.put("value", measurement.getValue());

            Point point = Point.measurement(sensorLotName)
                    .time(measurement.getTimeStamp(), TimeUnit.MILLISECONDS)
                    .addField("sensorName", measurement.getSensorName())
                    .addField("law", lawName)
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
