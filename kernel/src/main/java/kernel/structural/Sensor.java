package kernel.structural;

import kernel.Measurement;
import kernel.NamedElement;
import kernel.structural.laws.Law;

import java.util.UUID;

public class Sensor implements NamedElement {
    private String name;
    private Law law;

    public Sensor(Law law) {
        this.law = law;
        this.name = UUID.randomUUID().toString();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public Measurement generateNextMeasurement(int t) {
        return new Measurement(name, t, law.generateNextValue(t));
    }
}
