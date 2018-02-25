package kernel.datasources.laws;

import groovy.lang.Closure;
import groovy.lang.MissingMethodException;
import kernel.datasources.Measurement;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MathFunctionLaw implements DataSource {

    private String name;
    private Closure expression;
    private int counter;
    private boolean isExecutable;
    private List<Number> noise;

    public MathFunctionLaw() {
        this.counter = 0;
        this.name = "composite";
    }

    @Override
    public List<Measurement> generateNextMeasurement(double t) {
        expression.setResolveStrategy(Closure.DELEGATE_ONLY);
        expression.setDelegate(this); //just so the namespace is different and it crashes on recursions

        Double value;
        try {
            value = Double.valueOf(expression.call(counter).toString());
        } catch (MissingMethodException e) {
            throw new IllegalArgumentException("No Recursions allowed.");
        }
        counter++;

        if (noise != null) {
            Number inf = noise.get(0);
            Number sup = noise.get(1);
            double random = new Random().nextDouble();
            double noiseValue = inf.doubleValue() + (random * (sup.doubleValue() - inf.doubleValue()));
            value += noiseValue;
        }
        return Collections.singletonList(new Measurement<>(name, (long) t, value));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean isExecutable() {
        return isExecutable;
    }

    public void setExecutable(boolean executable) {
        isExecutable = executable;
    }

    public void setExpression(Closure expression) {
        this.expression = expression;
    }

    public void setNoise(List<Number> noise) {
        this.noise = noise;
    }
}
