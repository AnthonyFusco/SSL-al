package builders;

import groovy.lang.Closure;
import groovy.lang.MissingMethodException;
import kernel.structural.laws.MathFunctionLaw;

public class MathFunctionBuilder extends LawBuilder<MathFunctionLaw> {

    private Closure mapExpressionsConditions;
    private String LINECOUNTNAME = "LINECOUNTNAME";

    public MathFunctionBuilder(int definitionLine) {
        super(definitionLine);
    }

    public MathFunctionBuilder expression(Closure expression) {
        this.mapExpressionsConditions = expression;
        return this;
    }

    @Override
    public MathFunctionLaw build() {
        MathFunctionLaw law = new MathFunctionLaw();
        law.setExpression(mapExpressionsConditions);
        law.setExecutable(isExecutable());
        return law;
    }

    @Override
    public void validate() {
        if (mapExpressionsConditions == null) {
            addError(new IllegalArgumentException("Missing a body, please define a lambda. Example lam = { x -> x+1}"));
        } else {
            if (mapExpressionsConditions.getMaximumNumberOfParameters() != 1) {
                addError(new IllegalArgumentException("Math law should take only one parameter."));
            }

            mapExpressionsConditions.setResolveStrategy(Closure.DELEGATE_ONLY);
            mapExpressionsConditions.setDelegate(this); //just so the namespace is different and it crashes on recursions
            try {
                mapExpressionsConditions.call(1);
            } catch (MissingMethodException e) {
                addError(new IllegalArgumentException("No Recursions allowed."));
            }
        }

    }

}
