package builders;

import groovy.lang.Closure;
import groovy.lang.MetaMethod;
import groovy.lang.MissingMethodException;
import kernel.datasources.laws.MathFunctionLaw;

import java.util.List;

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
        mapExpressionsConditions.setResolveStrategy(Closure.DELEGATE_ONLY);
        mapExpressionsConditions.setDelegate(this); //just so the namespace is different and it crashes on recursions

        if (mapExpressionsConditions == null) {
            addError(new IllegalArgumentException("Missing a body, please define a lambda. Example lam = { x -> x+1}"));
        } else {
            if (mapExpressionsConditions.getMaximumNumberOfParameters() != 1) {
                addError(new IllegalArgumentException("Math law should take only one parameter."));
            }else{
                try {
                    mapExpressionsConditions.call(1);
                } catch (MissingMethodException e) {
                    addError(new IllegalArgumentException("No Recursions allowed."));
                }
            }
        }

    }

}
