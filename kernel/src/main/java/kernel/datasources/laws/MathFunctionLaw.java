package kernel.datasources.laws;

import groovy.lang.Closure;
import groovy.lang.MissingMethodException;
import kernel.datasources.Measurement;

import java.util.Collections;
import java.util.List;

public class MathFunctionLaw implements DataSource {

    private String name;
    private Closure expression;
    private int counter;
    private boolean isExecutable;

    public MathFunctionLaw() {
        this.counter = 0;
        this.name = "composite";
    }

    @Override
    public List<Measurement> generateNextMeasurement(double t) {
        expression.setResolveStrategy(Closure.DELEGATE_ONLY);
        expression.setDelegate(this); //just so the namespace is different and it crashes on recursions

        Object value;
        try {
            value = expression.call(counter);
        } catch (MissingMethodException e) {
            throw new IllegalArgumentException("No Recursions allowed.");
        }
        counter++;
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
}
