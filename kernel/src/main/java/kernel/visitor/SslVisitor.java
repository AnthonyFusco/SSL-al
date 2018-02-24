package kernel.visitor;

import kernel.Application;
import kernel.Measurement;
import kernel.structural.Simulation;
import kernel.structural.laws.DataSource;
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

        application.getExecutableSources().stream()
                .filter(DataSource::isExecutable)
                .forEach(source -> source.accept(this));

        application.getReplays().stream()
                .filter(DataSource::isExecutable)
                .forEach(replay -> replay.accept(this));
    }

    @Override
    public void visit(Simulation simulation) {
        double startTime = startDate.getTime();
        double endTime = endDate.getTime();

        double period = 1.0 / simulation.getFrequencyValue();

        int numberIterations = (int) ((endTime - startTime) / period);

        System.out.println("Starting the " + simulation.getName() + " " + simulation.getExecutableName() +
                " (" + numberIterations + " points)");

        for (double t = startDate.getTime(); t < endDate.getTime(); t += period) {
            List<Measurement> measurement = simulation.generateNextMeasurement(t);
            databaseHelper.sendToDatabase(
                    measurement,
                    simulation.getExecutableName() + "_",
                    "");
        }

        System.out.println(simulation.getName() + " done\n");
    }

    @Override
    public void visit(Replay replay) {
        List<Measurement> measurements = replay.generateNextMeasurement(startDate.getTime());
        System.out.println("Starting the replay " + replay.getExecutableName() +
                " (" + measurements.size() +" values)");
        databaseHelper.sendToDatabase(measurements, replay.getExecutableName() + "_", "Replay");
        System.out.println(replay.getName() + " done\n");
    }

}
