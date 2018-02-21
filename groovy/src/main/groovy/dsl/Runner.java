package dsl;

import kernel.Application;
import kernel.structural.EntityBuilder;
import kernel.structural.SensorsLot;
import kernel.structural.laws.DataSource;
import kernel.structural.replay.Replay;
import kernel.visitor.ExecutableSource;
import kernel.visitor.SslVisitor;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Runner {
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
            } if (dataSource instanceof ExecutableSource) {
                app.addExecutableSource((ExecutableSource) dataSource);
            } else {
                app.getDataSources().add(dataSource);
            }
        });
    }

    private void validateDataSources() {
        model.getDataSourcesBuilders().forEach(builder -> {
            builder.validate();
            builder.printErrors();
        });
    }

    private boolean anyError() {
        return model.getDataSourcesBuilders().stream().anyMatch(EntityBuilder::isInErrorState);
    }
}
