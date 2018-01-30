package kernel.visitor;

import kernel.Application;
import kernel.Measurement;
import kernel.structural.Sensor;
import kernel.structural.SensorsLot;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SslVisitor implements Visitor {

    @Override
    public void visit(Application application) {
        for (SensorsLot sensorsLot : application.getSensorsLots()) {
            visitSensorsLot(sensorsLot);
        }
    }

    private void visitSensorsLot(SensorsLot lot) {
        for (int t = 0; t < lot.getSimulationDuration(); t++) {
            List<String> measurements = new ArrayList<>();
            for (Sensor sensor : lot.getSensors()) {
                Measurement measurement = sensor.generateNextMeasurement(t);
                String data = lot.getName() + " value=" + measurement.getValue() + " " + measurement.getTimeStamp();
                measurements.add(data);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            postToDatabase(measurements);
        }
    }

    private void postToDatabase(List<String> measurements) {
        try {
            URL url = new URL("http://localhost:8086/write?db=influxdb");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");

            //write
            try (OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream())) {
                String toWrite = String.join(" \n ", measurements);
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

}
