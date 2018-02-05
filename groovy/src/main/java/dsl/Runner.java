package dsl;

import kernel.Application;
import kernel.structural.SensorsLot;
import kernel.structural.laws.Law;
import kernel.visitor.SslVisitor;

import java.util.List;

public class Runner {
    private SslModel model;

    public Runner(SslModel model) {
        this.model = model;
    }

    public void runSimulation() {
        final Application app = new Application();

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
        //TODO validate law builder
        return model.getLaws().stream().filter(law -> law.getName().equals(lawName)).findFirst().get();
    }
}
