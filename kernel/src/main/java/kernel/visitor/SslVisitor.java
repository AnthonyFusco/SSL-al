package kernel.visitor;

import kernel.Application;
import kernel.Measurement;
import kernel.structural.SensorsLot;
import kernel.structural.composite.Composite;
import kernel.structural.replay.Replay;

import java.util.Date;
import java.util.List;

public class SslVisitor implements Visitor {

    private DatabaseHelper databaseHelper = new InfluxDbHelper();
    private Date startDate;
    private Date endDate;

    public SslVisitor() {
        //ignore
    }

    public SslVisitor(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void visit(Application application) {
        this.startDate = application.getStartDate();
        this.endDate = application.getEndDate();

        for (SensorsLot sensorsLot : application.getSensorsLots()) {
            if (application.getToPlay().contains(sensorsLot.getName())) {
                sensorsLot.accept(this);
            }
        }

        for (Replay replay : application.getReplays()) {
            if (application.getToPlay().contains(replay.getName())) {
                replay.accept(this);
            }
        }

        for (Composite composite : application.getComposites()) {
            if (application.getToPlay().contains(composite.getName())) {
                composite.accept(this);
            }
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
        double startTime = startDate.getTime();
        double endTime = endDate.getTime();

        double period = 1.0 / sensorsLot.getFrequencyValue();

        int numberIterations = (int) ((endTime - startTime) / period);

        int sensorNumber = sensorsLot.getSensors().size();
        System.out.println("Starting the lot " + sensorsLot.getName() +
                " (" + sensorNumber + " sensors * " + numberIterations + " iterations = "
                + sensorNumber * numberIterations + " points)");

        for (double t = startDate.getTime(); t < endDate.getTime(); t += period) {
            List<Measurement> measurements = sensorsLot.generateNextMeasurement(t);
            databaseHelper.sendToDatabase(measurements, sensorsLot.getName(), sensorsLot.getLawBuilder().getName());
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
