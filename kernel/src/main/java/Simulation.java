package kernel;

import structural.laws.Law;
import structural.SensorsLot;

import java.util.List;

public class Simulation implements NamedElement {
    private List<SensorsLot> sensorsLots;
    private List<Law> laws;

    @Override
    public String getName() {
        return null;
    }
}
