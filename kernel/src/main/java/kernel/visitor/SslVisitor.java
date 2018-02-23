package kernel.visitor;

import kernel.Application;
import kernel.Measurement;
import kernel.structural.SensorsLot;
import kernel.structural.composite.Composite;
import kernel.structural.laws.DataSource;
import kernel.structural.replay.Replay;

import java.util.ArrayList;
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
    public void visit(ExecutableSource executableSource) {
        double startTime = startDate.getTime();
        double endTime = endDate.getTime();

        double period = 1.0 / executableSource.getFrequencyValue();

        int numberIterations = (int) ((endTime - startTime) / period);

        System.out.println("Starting the " + executableSource.getName() + " " + executableSource.getExecutableName() +
                " (" + numberIterations + " points)");

        for (double t = startDate.getTime(); t < endDate.getTime(); t += period) {
            List<Measurement> measurement = executableSource.generateNextMeasurement(t);
            databaseHelper.sendToDatabase(
                    measurement,
                    executableSource.getName() + "_",
                    "");
        }

        System.out.println(executableSource.getName() + " done\n");
    }

    @Override
    public void visit(Replay replay) {
        List<Measurement> measurements = replay.generateNextMeasurement(startDate.getTime());
        System.out.println("Starting the replay " + replay.getExecutableName() +
                " (" + measurements.size() +" values)");
        databaseHelper.sendToDatabase(measurements, replay.getName(), "Replay");
        System.out.println(replay.getName() + " done\n");
    }

}
