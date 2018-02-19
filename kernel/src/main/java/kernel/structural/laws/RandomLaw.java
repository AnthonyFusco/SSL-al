package kernel.structural.laws;

import kernel.Measurement;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomLaw implements DataSource {
    private String name;
    private Integer borneinf;
    private Integer bornesup;

    @Override
    public List<Measurement> generateNextMeasurement(double t) {
        int value = new Random()
                .nextInt(bornesup + 1 - borneinf) + borneinf;
        return Collections.singletonList(new Measurement<>(name, (long)t, value));
    }

    @Override
    public String getName() {
        return name;
    }

    public void setBorneInf(Integer borneInf) {
        this.borneinf = borneInf;
    }

    public void setBorneSup(Integer borneSup) {
        this.bornesup = borneSup;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
