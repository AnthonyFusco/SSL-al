package kernel.structural;

import kernel.Measurement;
import kernel.NamedElement;
import kernel.structural.laws.Law;

import java.time.Instant;
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
        //todo timestamp en fonction de t
        String timestamp = String.valueOf(Instant.now().getEpochSecond()) + "000000000";
        Measurement value = law.generateNextMeasurement(t);
        if (value == null) {
            return null;
        }
        return value;
//        return new Measurement(name, timestamp, law.generateNextMeasurement(t));
    }
}
