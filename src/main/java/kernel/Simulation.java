package kernel;

import kernel.structural.laws.Law;
import kernel.structural.SensorsLot;

import java.util.List;

public class Simulation implements NamedElement {
    private List<SensorsLot> sensorsLots;
    private List<Law> laws;

    @Override
    public String getName() {
        return null;
    }
}
