package kernel;

import kernel.structural.SensorsLot;
import kernel.structural.laws.Law;
import kernel.visitor.Visitable;
import kernel.visitor.Visitor;

import java.util.List;

public class Application implements NamedElement, Visitable {
    private List<SensorsLot> sensorsLots;
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setDeclaredSensorsLots(List<SensorsLot> sensorsLots) {
        this.sensorsLots = sensorsLots;
    }

    public List<SensorsLot> getSensorsLots() {
        return sensorsLots;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}