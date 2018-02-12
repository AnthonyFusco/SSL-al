package builders;

import dsl.SslModel;
import groovy.lang.Closure;
import kernel.structural.laws.MathFunctionLaw;


public class MathFunctionBuilder extends LawBuilder<MathFunctionLaw> {

    private Closure mapExpressionsConditions;

    public MathFunctionBuilder(String lawName) {
        super(lawName);
    }


    public MathFunctionBuilder withExpressions(Closure expression) {
        this.mapExpressionsConditions = expression;
        return this;
    }

    @Override
    public MathFunctionLaw build() {
        MathFunctionLaw law = new MathFunctionLaw();
        law.setName(getLawName());
        law.setExpression(mapExpressionsConditions);
        return law;
    }

    @Override
    public void validate(SslModel model) {
        String name = getLawName();
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("The name of a MathFunction must not be empty");
        }

        if (mapExpressionsConditions == null) {
            throw new IllegalArgumentException("Missing a body for" + name +
                    ", please define a lambda. Example lam = { x -> x+1}");
        }

        if (mapExpressionsConditions.getMaximumNumberOfParameters() != 1){
            throw new IllegalArgumentException("Math laws are function of time, they should take only one parameter.");
        }
    }
}
