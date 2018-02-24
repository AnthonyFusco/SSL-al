package dsl;

import builders.EntityBuilder;
import kernel.Application;
import kernel.datasources.laws.DataSource;
import kernel.visitor.InfluxDbHelper;
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

        buildDataSources(app);

        SslVisitor visitor = new SslVisitor(new InfluxDbHelper());
        app.accept(visitor);
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

    private void buildDataSources(Application app) {
        model.getDataSourcesBuilders().forEach(builder -> {
            DataSource dataSource = builder.build();
            app.addDataSource(dataSource);
        });
    }
}