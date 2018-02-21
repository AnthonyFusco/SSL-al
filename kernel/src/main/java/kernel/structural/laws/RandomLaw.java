package kernel.structural.laws;

import kernel.Measurement;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomLaw implements DataSource {
    private String name;
    private Integer rangeInf = 0;
    private Integer rangeSup = 10;
    private boolean isExecutable;

    public RandomLaw() {
        this.name = "RandomLaw";
    }

    @Override
    public List<Measurement> generateNextMeasurement(double t) {
        int value = new Random()
                .nextInt(rangeSup + 1 - rangeInf) + rangeInf;
        return Collections.singletonList(new Measurement<>(name, (long)t, value));
    }

    @Override
    public String getName() {
        return name;
    }

    public void setBorneInf(Integer borneInf) {
        this.rangeInf = borneInf;
    }

    public void setBorneSup(Integer borneSup) {
        this.rangeSup = borneSup;
    }

    @Override
    public boolean isExecutable() {
        return isExecutable;
    }

    public void setExecutable(boolean executable) {
        isExecutable = executable;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
