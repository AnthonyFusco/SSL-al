package kernel.structural.laws;

import kernel.Measurement;

import java.util.Random;

public class RandomLaw implements Law {
    private String name;
    private Integer borneinf;
    private Integer bornesup;

    @Override
    public Measurement generateNextMeasurement(double t) {
        int value = new Random()
                .nextInt(bornesup + 1 - borneinf) + borneinf;
        return new Measurement<>(name, (long)t, value);
    }

    @Override
    public String getName() {
        return name;
    }

    public void setBorneinf(Integer borneinf) {
        this.borneinf = borneinf;
    }

    public void setBornesup(Integer bornesup) {
        this.bornesup = bornesup;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
