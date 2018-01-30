package kernel;

import kernel.structural.SensorsLot;
import kernel.structural.laws.Law;

import java.util.List;

public class Application implements NamedElement {
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

    public void setDeclaredLaws(List<Law> declaredLaws) {
        this.laws = declaredLaws;
    }

    public List<Law> getLaws() {
        return laws;
    }

    public List<SensorsLot> getSensorsLots() {
        return sensorsLots;
    }
}