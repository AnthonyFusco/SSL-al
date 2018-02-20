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

        app.setToPlay(model.getToPlay());

        dispatchDataSourcesByType(app);

//        addLawToSensorLot(app);

//        addSensorLotToComposite(app);

        SslVisitor visitor = new SslVisitor();
        app.accept(visitor);
    }

    private void dispatchDataSourcesByType(Application app) {
        model.getDataSourcesBuilders().forEach(builder -> {
            DataSource dataSource = builder.build();
            if (dataSource instanceof Replay) {
                app.addReplay((Replay) dataSource);
            } /*else if (dataSource instanceof SensorsLot) {
                ((SensorsLot) dataSource).generatesSensors();
                app.addSensorLot((SensorsLot) dataSource);
            } else if (dataSource instanceof Composite) {
                app.addComposite((Composite) dataSource);
            }*/ else if (dataSource instanceof ExecutableSource) {
                app.addExecutableSource((ExecutableSource) dataSource);
            } else {
                app.getDataSources().add(dataSource);
            }
        });
    }

//    private void addLawToSensorLot(Application app) {
//        app.getSensorsLots().forEach(lot -> {
//            DataSource d = findLawByName(app, lot.getLawName());
//            lot.generatesSensors(d);
//        });
//    }

/*    private void addSensorLotToComposite(Application app) {
        app.getComposites().forEach(composite -> {
            List<SensorsLot> lots = composite.getSensorsLotsNames().stream()
                    .map(lotName -> findSensorLotByName(app, lotName)).collect(Collectors.toList());
            composite.setSensorsLots(lots);
        });
    }*/

    private void validateDataSources() {
        model.getDataSourcesBuilders().forEach(builder -> {
            builder.validate();
            builder.printErrors();
        });
    }

    private boolean anyError() {
        return model.getDataSourcesBuilders().stream().anyMatch(EntityBuilder::isInErrorState);
    }

 /*   private DataSource findLawByName(Application app, String lawName) {
        Optional<DataSource> dataSourceOptional =
                app.getDataSources().stream().filter(law -> law.getName().equals(lawName)).findFirst();
        return dataSourceOptional.orElse(null);
    }

    private SensorsLot findSensorLotByName(Application app, String lotName) {
        Optional<SensorsLot> lotOpt =
                app.getSensorsLots().stream().filter(sensorsLot -> sensorsLot.getName().equals(lotName)).findFirst();
        return lotOpt.orElse(null);
    }*/
}
