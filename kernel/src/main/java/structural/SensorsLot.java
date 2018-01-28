package structural;

import kernel.NamedElement;
import structural.laws.Law;

import java.util.List;

public class SensorsLot implements NamedElement {
    private List<Sensor> sensors;
    private Law law;
    private int sensorsNumber;

    @Override
    public String getName() {
        return null;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }
}
