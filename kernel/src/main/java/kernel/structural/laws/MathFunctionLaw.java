package kernel.structural.laws;

import kernel.Measurement;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.HashMap;
import java.util.Map;

import static kernel.structural.laws.MathFunctionReturnType.INT;

public class MathFunctionLaw implements Law {

    private String name;
    private static final double DOOM_VALUE = -9999;

    public MathFunctionReturnType domain;

    //Formules <valeur : condition>
    public Map<String,String> funcs;

    public MathFunctionLaw(){
        funcs = new HashMap<>();
    }

    public void setDomain(MathFunctionReturnType domain) {
        this.domain = domain;
    }

    public void addFunction(String value, String condition){
       funcs.put(value,condition);
    }

    @Override
    public Measurement generateNextMeasurement(double t) {
        Object value = DOOM_VALUE;
        Argument x = new Argument("x");
        x.setArgumentValue(t);
        if(domain == INT) {
            String toEvaluateCondition = "iff(";
            for (Map.Entry<String, String> entry : funcs.entrySet()) {
                toEvaluateCondition += entry.getValue() + "," + entry.getKey() + ";";
            }
            toEvaluateCondition = toEvaluateCondition.substring(0, toEvaluateCondition.length() - 1);
            toEvaluateCondition += ")";

            Expression e = new Expression(toEvaluateCondition, x);
            double res = e.calculate();
            //if (Double.isNaN(res)) //throw NaN exception
            value = res;
        }else {
            for (Map.Entry<String, String> entry : funcs.entrySet()) {
                Expression e2 = new Expression("if(" + entry.getValue() + ",1,0)", x);
                double tmp = e2.calculate();
                if (tmp == 1) {
                    value = entry.getValue();
                }
            }
        }
        return new Measurement(name, System.currentTimeMillis(), value);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    public void setFuncs(Map<String, String> funcs) {
        this.funcs = funcs;
    }
}
