package kernel.structural.laws;

import kernel.Measurement;

import java.util.ArrayList;

public class MathFunctionLaw implements Law {

    private String name;

    //Valeurs retournees
    public enum domaineType {BOOL,STRING,INT};
    public domaineType domaine;

    //Formules maths
    public String formuleInegalite;
    public String operator;
    public ArrayList<FunctionExpression> functions;

    public MathFunctionLaw(String function, domaineType domaine){
        String[] tmp = function.split("si");
        functions = new ArrayList<>();

        this.domaine = domaine;
    }

    public void addFunctionExpression(String s){
        String [] tmp = s.split("si");
        FunctionExpression f = new FunctionExpression(tmp[0],tmp[1]);
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

        Measurement m = new Measurement("name",t+"",value);
        return m;
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
