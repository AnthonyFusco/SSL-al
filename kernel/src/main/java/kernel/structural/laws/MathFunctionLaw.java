package kernel.structural.laws;

import kernel.Measurement;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class MathFunctionLaw implements Law {

    private String name;

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
        Object value = -1;
        Iterator mapIterator = this.funcs.entrySet().iterator();
        switch (domain){
            case INT:
                //Boucle over map
                //doEvalConditions
                //if condition -> doEvalValue
                //return value and BREAK

            default:
                //Boucle over map
                //doEvalConditions
                //if cond -> return  and BREAK

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

}
