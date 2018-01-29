package kernel;

import kernel.generator.Visitable;
import kernel.generator.Visitor;
import kernel.structural.SensorsLot;
import kernel.structural.laws.Law;

import java.util.List;

public class Application implements NamedElement, Visitable {
    private List<SensorsLot> sensorsLots;
    private List<Law> laws;
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

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void setDeclaredLaws(List<Law> declaredLaws) {
        this.laws = declaredLaws;
    }
}