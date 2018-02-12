package kernel.visitor;

import kernel.Measurement;
import org.influxdb.InfluxDB;
import org.influxdb.InfluxDBFactory;
import org.influxdb.dto.BatchPoints;
import org.influxdb.dto.Point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class InfluxDbHelper implements DatabaseHelper {

    @Override
    public void sendToDatabase(List<Measurement> measurements, String sensorLotName, String lawName) {
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