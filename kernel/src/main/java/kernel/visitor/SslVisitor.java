package kernel.visitor;

import kernel.Application;
import kernel.Measurement;
import kernel.structural.Sensor;
import kernel.structural.SensorsLot;
import kernel.structural.replay.Replay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SslVisitor implements Visitor {

    private DatabaseHelper databaseHelper = new InfluxDbHelper();

    public SslVisitor() {
    }

    public SslVisitor(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void visit(Application application) {
        for (SensorsLot sensorsLot : application.getSensorsLots()) {
            visitSensorsLot(sensorsLot, application.getStartDate(), application.getEndDate());
        }
        for (Replay replay : application.getReplays()) {
            visitReplay(replay, application.getStartDate());
        }
    }

    private void visitReplay(Replay replay, Date startDate) {
        List<Measurement> measurements = replay.getMeasurements(startDate);
        System.out.println("Starting the replay " + replay.getName() + " (" + measurements.size() + " values)");
        databaseHelper.sendToDatabase(measurements, replay.getName(), "Replay");
        System.out.println(replay.getName() + " done");
    }

    private void visitSensorsLot(SensorsLot lot, Date startDate, Date endDate) {
        int sensorNumber = lot.getSensors().size();
        double period = 1.0 / lot.getFrequencyValue();
        double startTime = startDate.getTime();
        double endTime = endDate.getTime();
        int numberIterations = (int) ((endTime - startTime) / period);
        System.out.println("Starting the lot " + lot.getName() +
                " (" + sensorNumber + " sensors * " + numberIterations + " iterations = "
                + sensorNumber * numberIterations + " points)");

        for (double t = startDate.getTime(); t < endDate.getTime(); t += period) {
            List<Measurement> measurements = new ArrayList<>();
            for (Sensor sensor : lot.getSensors()) {
                Measurement measurement = sensor.generateNextMeasurement(t);
                measurements.add(measurement);
            }
            databaseHelper.sendToDatabase(measurements, lot.getName(), lot.getLawName());
        }

        System.out.println(lot.getName() + " done");
    }
}
