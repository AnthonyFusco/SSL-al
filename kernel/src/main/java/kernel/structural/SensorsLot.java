package kernel.structural;

import kernel.NamedElement;
import kernel.structural.laws.Law;

import java.util.List;

public class SensorsLot implements NamedElement {
    private List<Sensor> sensors;
    private Law law;
    private int sensorsNumber;
    private String name;

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }
}
