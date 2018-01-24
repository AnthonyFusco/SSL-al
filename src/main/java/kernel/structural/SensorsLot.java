package kernel.structural;

import kernel.NamedElement;
import kernel.structural.laws.Law;

import java.util.List;

public class SensorsLot implements NamedElement {
    private List<Sensor> sensors;
    private Law law;

    @Override
    public String getName() {
        return null;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }
}
