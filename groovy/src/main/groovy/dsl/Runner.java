package dsl;

import kernel.Application;
import builders.EntityBuilder;
import kernel.datasources.executables.replay.Replay;
import kernel.datasources.executables.simulations.Simulation;
import kernel.datasources.laws.DataSource;
import kernel.visitor.SslVisitor;

import java.util.Date;

public class Runner {
    public static String currentFile = "";
    private SslModel model;

    public Runner(SslModel model) {
        this.model = model;
    }

    public void runSimulation(Date startDate, Date endDate) {
        final Application app = new Application();
        app.setStartDate(startDate);
        app.setEndDate(endDate);

        validateDataSources();

        if (anyError()) return;

        dispatchDataSourcesByType(app);

        SslVisitor visitor = new SslVisitor();
        app.accept(visitor);
    }

    private void dispatchDataSourcesByType(Application app) {
        model.getDataSourcesBuilders().forEach(builder -> {
            DataSource dataSource = builder.build();
            if (dataSource instanceof Replay) {
                app.addReplay((Replay) dataSource);
            } else if (dataSource instanceof Simulation) {
                app.addSimulation((Simulation) dataSource);
            }
        });
    }

    private void validateDataSources() {
        model.getDataSourcesBuilders().forEach(builder -> {
            builder.validate();
            builder.printWarningsErrors();
        });
    }

    private boolean anyError() {
        return model.getDataSourcesBuilders().stream().anyMatch(EntityBuilder::isInErrorState);
    }
}
