package kernel.visitor;

import kernel.Application;
import kernel.datasources.Measurement;
import kernel.datasources.executables.PhysicalDataSource;
import kernel.datasources.executables.replay.Replay;
import kernel.datasources.executables.simulations.Simulation;
import kernel.datasources.laws.DataSource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SslVisitor implements Visitor {

    private DatabaseHelper databaseHelper;
    private Date startDate;
    private Date endDate;

    public SslVisitor(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
    }

    @Override
    public void visit(Application application) {
        this.startDate = application.getStartDate();
        this.endDate = application.getEndDate();

        application.getDataSources().stream()
                .filter(DataSource::isExecutable)
                .map(dataSource -> (PhysicalDataSource) dataSource)
                .forEach(executableSource -> executableSource.accept(this));
    }

    @Override
    public void visit(Simulation simulation) {
        double startTime = startDate.getTime();
        double endTime = endDate.getTime();

        double period = 1.0 / simulation.getFrequencyValue();

        int numberIterations = (int) ((endTime - startTime) / period);

        System.out.println("Starting the " + simulation.getName() + " " + simulation.getExecutableName() +
                " (" + numberIterations + " points per sensors)");

        List<Measurement> measurements = new ArrayList<>();
        for (double t = startDate.getTime(); t < endDate.getTime(); t += period) {
            measurements.addAll(simulation.generateNextMeasurement(t));
            if (measurements.size() > 15_000) {
                flushSimulationToDatabase(simulation, measurements);
                measurements = new ArrayList<>();
            }
        }
        flushSimulationToDatabase(simulation, measurements);
        System.out.println(simulation.getExecutableName() + " done\n");
    }

    private void flushSimulationToDatabase(Simulation simulation, List<Measurement> measurements) {
        databaseHelper.sendToDatabase(measurements, simulation.getExecutableName() + "_", "");
    }

    @Override
    public void visit(Replay replay) {
        List<Measurement> measurements = replay.generateNextMeasurement(startDate.getTime());
        System.out.println("Starting the replay " + replay.getExecutableName() +
                " (" + measurements.size() + " values)");
        databaseHelper.sendToDatabase(measurements, replay.getExecutableName() + "_", "Replay");
        System.out.println(replay.getExecutableName() + " done\n");
    }

}
