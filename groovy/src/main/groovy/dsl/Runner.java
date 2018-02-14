package dsl;

import builders.CompositeBuilder;
import builders.LawBuilder;
import builders.ReplayBuilder;
import builders.SensorsLotBuilder;
import kernel.Application;
import kernel.structural.SensorsLot;
import kernel.structural.composite.Composite;
import kernel.structural.laws.Law;
import kernel.structural.replay.Replay;
import kernel.visitor.SslVisitor;

import java.util.ArrayList;
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

        model.getLawsBuilders().forEach(builder -> {
            builder.validate(model);
            builder.printErrors();
        });

        for (LawBuilder lawBuilder : model.getLawsBuilders()) {
            if (lawBuilder.isInErrorState()) {
                return;
            }
        }

        model.getLawsBuilders().forEach(builder -> {
            Law law = builder.build();
            app.getLaws().add(law);
        });

        model.getReplayBuilders().forEach(builder -> {
            builder.validate(model);
            builder.printErrors();
        });

        for (ReplayBuilder replayBuilder : model.getReplayBuilders()) {
            if (replayBuilder.isInErrorState()) {
                return;
            }
        }

        model.getReplayBuilders().forEach(builder -> {
            Replay replay = builder.build();
            app.getReplays().add(replay);
        });

        model.getSensorsLotBuilders().forEach(builder -> {
            builder.validate(model);
            builder.printErrors();
        });

        for (SensorsLotBuilder lotBuilder : model.getSensorsLotBuilders()) {
            if (lotBuilder.isInErrorState()) {
                return;
            }
        }

        model.getSensorsLotBuilders().forEach(builder -> {
            SensorsLot lot = builder.build();
            Law law = findLawByName(lot.getLawName());
            lot.generatesSensors(law);
            app.getSensorsLots().add(lot);
        });

        model.getCompositesBuilders().forEach(builder -> {
            builder.validate(model);
            builder.printErrors();
        });

        for (CompositeBuilder compositeBuilder : model.getCompositesBuilders()) {
            if (compositeBuilder.isInErrorState()) {
                return;
            }
        }

        model.getCompositesBuilders().forEach(builder -> {
            Composite composite = builder.build();
            List<SensorsLot> lots = composite.getSensorsLotsNames().stream()
                    .map(lotName -> findSensorLotByName(app, lotName)).collect(Collectors.toList());
            composite.setSensorsLots(lots);
            app.addComposite(composite);
        });

        SslVisitor visitor = new SslVisitor();
        visitor.visit(app);
    }

    private Law findLawByName(String lawName) {
        Optional<LawBuilder> builderOpt =
                model.getLawsBuilders().stream().filter(law -> law.getLawName().equals(lawName)).findFirst();
        if (builderOpt.isPresent()) {
            LawBuilder builder = builderOpt.get();
            //builder.validate(model);
            return builder.build();
        }
        return null;
    }

    private SensorsLot findSensorLotByName(Application app, String lotName) {
        Optional<SensorsLot> lotOpt =
                app.getSensorsLots().stream().filter(sensorsLot -> sensorsLot.getName().equals(lotName)).findFirst();
        return lotOpt.orElse(null);
    }
}
