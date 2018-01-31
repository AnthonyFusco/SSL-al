package kernel.structural.laws;

import kernel.Measurement;

import java.time.Instant;
import java.util.ArrayList;

public class MathFunctionLaw implements Law {

    public DomainType domain;
    //Formules maths
    public String formuleInegalite;

    ;
    public String operator;
    public ArrayList<FunctionExpression> functions;
    private String name;
    public MathFunctionLaw() {
        functions = new ArrayList<>();
    }

    public void setDomain(DomainType domain) {
        this.domain = domain;
    }

    public void addFunctionExpression(String s) {
        String[] tmp = s.split("si");
        FunctionExpression f = new FunctionExpression(tmp[0], tmp[1]);
        f.findInegaliteRight();
        f.findFormuleLeft();
    }

    @Override
    public Measurement generateNextMeasurement(int t) {
        int value = -1;
        for (FunctionExpression f : functions) {
            if (f.calculateRight(t)) {
                value = f.calculateLeft(t);
            }
        }

        return new Measurement("name", System.currentTimeMillis(), value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    //Valeurs retournees
    public enum DomainType {
        BOOL, STRING, INT
    }
}
