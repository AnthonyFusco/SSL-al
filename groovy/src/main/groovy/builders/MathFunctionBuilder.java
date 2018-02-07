package builders;

import dsl.SslModel;
import groovy.lang.Closure;
import kernel.structural.laws.MathFunctionLaw;
import kernel.structural.laws.MathFunctionReturnType;

import java.util.Map;

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

    }
}
