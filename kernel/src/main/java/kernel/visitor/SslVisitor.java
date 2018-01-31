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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SslVisitor implements Visitor {

    @Override
    public void visit(Application application) {
        for (SensorsLot sensorsLot : application.getSensorsLots()) {
            visitSensorsLot(sensorsLot);
        }
    }

    private void visitSensorsLot(SensorsLot lot) {
        for (int t = 0; t < lot.getSimulationDuration(); t++) {
            List<Measurement> measurements = new ArrayList<>();
            for (Sensor sensor : lot.getSensors()) {
                Measurement measurement = sensor.generateNextMeasurement(t);
                if (measurement == null) {
                    continue; //todo handle the case ?
                }
//                String data = lot.getName() + " value=" + measurement.getValue() + " " + measurement.getTimeStamp();
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

    //todo not here
    private void postToDatabase(List<String> measurements) {
        try {
            URL url = new URL("http://localhost:8086/write?db=influxdb");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");

            //write
            try (OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream())) {
                String toWrite = String.join("\n", measurements);
                System.out.println(toWrite);
                writer.write(toWrite);
            }
            //read response
            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                String decodedString;
                while ((decodedString = in.readLine()) != null) {
                    System.out.println(decodedString);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

        influxDB.write(batchPoints);
//        Query query = new Query("SELECT * FROM " + measurements.get(0).getSensorName(), dbName);
//        System.out.println(influxDB.query(query));
    }

}
