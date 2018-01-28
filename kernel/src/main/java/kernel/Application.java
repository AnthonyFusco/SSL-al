package kernel;

import kernel.generator.Visitable;
import kernel.generator.Visitor;
import kernel.structural.SensorsLot;
import kernel.structural.laws.Law;

import java.util.List;

public class Application implements NamedElement, Visitable {
    private List<SensorsLot> sensorsLots;
    private List<Law> laws;

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    public void setSensorsLots(List<SensorsLot> sensorsLots) {
        this.sensorsLots = sensorsLots;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}