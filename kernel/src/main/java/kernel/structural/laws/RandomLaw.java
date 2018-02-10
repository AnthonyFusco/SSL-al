package kernel.structural.laws;

import kernel.Measurement;

import java.util.Random;

public class RandomLaw implements Law {
    private String name;

    @Override
    public Measurement generateNextMeasurement(double t) {
        return new Measurement<>(name, (long)t, new Random().nextInt() % 10);
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
