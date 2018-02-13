package kernel.visitor;

import kernel.Application;
import kernel.Measurement;
import kernel.structural.Sensor;
import kernel.structural.SensorsLot;
import kernel.structural.composite.Composite;
import kernel.structural.replay.Replay;
import kernel.units.Duration;
import kernel.units.Frequency;
import kernel.units.TimeUnit;

import java.util.*;

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
        for (Composite composite : application.getComposites()) {
            visitComposite(composite, application.getStartDate(), application.getEndDate());
        }
    }

    private void visitComposite(Composite composite, Date startDate, Date endDate) {
        double startTime = startDate.getTime();
        double endTime = endDate.getTime();
        double period = 1.0 / composite.getFrequency().getValue();
        int numberIterations = (int) ((endTime - startTime) / period);
        System.out.println("Starting the composite " + composite.getName() + " (" + numberIterations + " points)");

        for (double t = startDate.getTime(); t < endDate.getTime(); t += period) {
            Measurement measurement = composite.generateNextMeasurement(t);
            databaseHelper.sendToDatabase(
                    Collections.singletonList(measurement),
                    "composite_",
                    "composite"); //composite lot law
        }

        System.out.println(composite.getName() + " done\n");
    }

    private void visitReplay(Replay replay, Date startDate) {
        List<Measurement> measurements = replay.getMeasurements(startDate);
        System.out.println("Starting the replay " + replay.getName() + " (" + measurements.size() + " values)");
        databaseHelper.sendToDatabase(measurements, replay.getName(), "Replay");
        System.out.println(replay.getName() + " done\n");
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

        System.out.println(lot.getName() + " done\n");
    }
}
