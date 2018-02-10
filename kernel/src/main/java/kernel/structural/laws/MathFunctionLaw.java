package kernel.structural.laws;

import kernel.Measurement;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;
import groovy.lang.Closure;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static kernel.structural.laws.MathFunctionReturnType.INT;

public class MathFunctionLaw implements Law {

    private String name;
    private Closure expression;

    @Override
    public Measurement generateNextMeasurement(double t) {
        Integer result = (Integer) expression.call(t); //todo always an int ??
        long value = new Long(result);

        return new Measurement<>(name, (long)t, value);
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
