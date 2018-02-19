package kernel.visitor;

import kernel.Application;
import kernel.Measurement;
import kernel.structural.Sensor;
import kernel.structural.SensorsLot;
import kernel.structural.composite.Composite;
import kernel.structural.replay.Replay;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SslVisitor implements Visitor {

    private DatabaseHelper databaseHelper = new InfluxDbHelper();
    private Date startDate;
    private Date endDate;

    public SslVisitor() {
    }

    public SslVisitor(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void visit(Application application) {
        this.startDate = application.getStartDate();
        this.endDate = application.getEndDate();

        for (SensorsLot sensorsLot : application.getSensorsLots()) {
            visit(sensorsLot);
        }
        for (Replay replay : application.getReplays()) {
            visit(replay);
        }
        for (Composite composite : application.getComposites()) {
            visit(composite);
        }
    }

    @Override
    public void visit(Composite composite) {
        double startTime = startDate.getTime();
        double endTime = endDate.getTime();
        double period = 1.0 / composite.getFrequency().getValue();
        int numberIterations = (int) ((endTime - startTime) / period);
        System.out.println("Starting the composite " + composite.getName() + " (" + numberIterations + " points)");

        for (double t = startDate.getTime(); t < endDate.getTime(); t += period) {
            List<Measurement> measurement = composite.generateNextMeasurement(t);
            databaseHelper.sendToDatabase(
                    measurement,
                    "composite_",
                    "composite"); //composite lot law
        }

        System.out.println(composite.getName() + " done\n");
    }

    @Override
    public void visit(SensorsLot sensorsLot) {
        int sensorNumber = sensorsLot.getSensors().size();
        double period = 1.0 / sensorsLot.getFrequencyValue();
        double startTime = startDate.getTime();
        double endTime = endDate.getTime();
        int numberIterations = (int) ((endTime - startTime) / period);
        System.out.println("Starting the lot " + sensorsLot.getName() +
                " (" + sensorNumber + " sensors * " + numberIterations + " iterations = "
                + sensorNumber * numberIterations + " points)");

        for (double t = startDate.getTime(); t < endDate.getTime(); t += period) {
            List<Measurement> measurements = sensorsLot.generateNextMeasurement(t);
            databaseHelper.sendToDatabase(measurements, sensorsLot.getName(), sensorsLot.getLawName());
        }

        System.out.println(sensorsLot.getName() + " done\n");
    }

    @Override
    public void visit(Replay replay) {
        List<Measurement> measurements = replay.generateNextMeasurement(startDate.getTime());
        System.out.println("Starting the replay " + replay.getName() + " (" + measurements.size() + " values)");
        databaseHelper.sendToDatabase(measurements, replay.getName(), "Replay");
        System.out.println(replay.getName() + " done\n");
    }

}
