package kernel.structural.laws;

import kernel.Measurement;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MathFunctionLaw implements Law {

    private String name;
    private static final double DOOM_VALUE = -9999;

    //Valeurs retournees
    public enum DomainType {BOOL,STRING,INT};
    public DomainType domain;

    //Formules <valeur : condition>
    public Map<String,String> funcs;

    public MathFunctionLaw(){
        funcs = new HashMap<>();
    }

    public void setDomain(DomainType domain) {
        this.domain = domain;
    }

    public void addFunction(String value, String condition){
       funcs.put(value,condition);
    }

    @Override
    public Measurement generateNextMeasurement(int t) {
        Object value = DOOM_VALUE;
        Argument x = new Argument("x");
        x.setArgumentValue(t);

        switch (domain){
            case INT:
                String toEvaluateCondition = "iff(";
                for(Map.Entry<String,String> entry : funcs.entrySet()){
                    toEvaluateCondition+= entry.getKey() +"," + entry.getValue();
                }
                toEvaluateCondition +=")";
                Expression e = new Expression(toEvaluateCondition, x);
                double res = e.calculate();

                if(Double.isNaN(res)) //throw NaN exception
                value = res;

            default:
                for(Map.Entry<String,String> entry : funcs.entrySet()){
                    //If eval = 1 alors on renvoi la string/bool sinon rien
                    Expression e2 = new Expression("if("+entry.getKey()+",1,0)",x);
                    double tmp = e2.calculate();
                    if(tmp == 1){
                        value = entry.getValue();
                    }
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
        this.name=name;
    }

    public void setFuncs(Map<String, String> funcs) {
        this.funcs = funcs;
    }
}
