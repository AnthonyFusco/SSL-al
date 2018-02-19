package kernel.structural.laws;

import kernel.Measurement;
import groovy.lang.Closure;

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
        Object value = expression.call(counter);
        counter++;
        return Collections.singletonList(new Measurement<>(name, (long) t, value));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }


    public void setExpression(Closure expression) {
        this.expression = expression;
    }
}
