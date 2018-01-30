package kernel.structural.laws;

import kernel.Measurement;
import kernel.structural.Sensor;
import kernel.structural.SensorsLot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomLaw implements Law {
    private String name;

    @Override
    public List<Measurement> play(SensorsLot lot) {
        List<Measurement> result = new ArrayList<>();
        for (Sensor sensor : lot.getSensors()) {
            for (int i = 0; i < 50; i++) {
                int value = new Random().nextInt();
                result.add(new Measurement(sensor, i, value));
            }
        }
        return result;
    }

    @Override
    public Law applyLaw(Law law) {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
