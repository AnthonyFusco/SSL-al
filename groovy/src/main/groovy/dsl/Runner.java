package dsl;

import builders.LawBuilder;
import builders.ReplayBuilder;
import kernel.Application;
import kernel.structural.SensorsLot;
import kernel.structural.laws.Law;
import kernel.structural.replay.Replay;
import kernel.visitor.SslVisitor;

import java.util.Date;
import java.util.Optional;

public class Runner {
    private SslModel model;

    public Runner(SslModel model) {
        this.model = model;
    }

    public void runSimulation(Date startDate, Date endDate) {
        final Application app = new Application();
        app.setStartDate(startDate);
        app.setEndDate(endDate);

        //todo abstract that !!!

        model.getLawsBuilders().forEach(builder -> builder.validate(model));

        model.getLawsBuilders().forEach(builder -> {
            Law law = builder.build();
            app.getLaws().add(law);
        });

        model.getReplayBuilders().forEach(builder -> builder.validate(model));

        model.getReplayBuilders().forEach(builder -> {
            Replay replay = builder.build();
            app.getReplays().add(replay);
        });

        model.getSensorsLotBuilders().forEach(builder -> builder.validate(model));

        model.getSensorsLotBuilders().forEach(builder -> {
            SensorsLot lot = builder.build();
            Law law = findLawByName(lot.getLawName());
            lot.generatesSensors(law);
            app.getSensorsLots().add(lot);
        });

        SslVisitor visitor = new SslVisitor();
        visitor.visit(app);
    }

    private Law findLawByName(String lawName) {
        Optional<LawBuilder> builderOpt =
                model.getLawsBuilders().stream().filter(law -> law.getLawName().equals(lawName)).findFirst();
        if (builderOpt.isPresent()) {
            LawBuilder builder = builderOpt.get();
            builder.validate(model);
            return builder.build();
        }

        return null;
    }
}
