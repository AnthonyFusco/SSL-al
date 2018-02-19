package kernel.structural.laws;

import groovy.lang.Closure;
import groovy.lang.MissingMethodException;
import kernel.Measurement;

import java.util.Collections;
import java.util.List;

public class MathFunctionLaw implements DataSource {

    private String name;
    private Closure expression;
    private int counter;

    public MathFunctionLaw() {
        counter = 0;
    }

    @Override
    public List<Measurement> generateNextMeasurement(double t) {
        expression.setResolveStrategy(Closure.DELEGATE_ONLY);
        expression.setDelegate(this); //just so the namespace is different and it crashes on recursions

        Object value;
        try {
            value = expression.call(counter);
        } catch (MissingMethodException e) {
            throw new IllegalArgumentException("No Recursions allowed, sorry bro.");
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


    public void setExpression(Closure expression) {
        this.expression = expression;
    }
}
