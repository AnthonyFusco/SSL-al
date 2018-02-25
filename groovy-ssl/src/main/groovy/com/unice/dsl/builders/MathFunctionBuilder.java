package com.unice.dsl.builders;

import groovy.lang.Closure;
import groovy.lang.MissingMethodException;
import kernel.datasources.laws.MathFunctionLaw;

import java.math.BigDecimal;
import java.util.List;

public class MathFunctionBuilder extends LawBuilder<MathFunctionLaw> {

    private Closure mapExpressionsConditions;
    private String LINECOUNTNAME = "LINECOUNTNAME";
    private List<Number> noise;

    public MathFunctionBuilder(int definitionLine) {
        super(definitionLine);
    }

    public MathFunctionBuilder expression(Closure expression) {
        this.mapExpressionsConditions = expression;
        return this;
    }

    public MathFunctionBuilder noise(List<Number> noise) {
        this.noise = noise;
        return this;
    }

    @Override
    public MathFunctionLaw build() {
        MathFunctionLaw law = new MathFunctionLaw();
        law.setExpression(mapExpressionsConditions);
        law.setNoise(noise);
        law.setExecutable(isExecutable());
        return law;
    }

    @Override
    public void validate() {
        mapExpressionsConditions.setResolveStrategy(Closure.DELEGATE_ONLY);
        mapExpressionsConditions.setDelegate(this); //just so the namespace is different and it crashes on recursions

        //duplicate :/
        if (noise != null) {
            if (noise.size() != 2) {
                addError(new IllegalArgumentException("You must specify a valid noise interval"));
            } else if (noise.get(0).doubleValue() >= noise.get(1).doubleValue()) {
                addError(new IllegalArgumentException("the range is reversed"));
            }
        }

        if (mapExpressionsConditions == null) {
            addError(new IllegalArgumentException("Missing a body, please define a lambda. Example lam = {x -> x+1}"));
        } else {
            if (mapExpressionsConditions.getMaximumNumberOfParameters() != 1) {
                addError(new IllegalArgumentException("Math law should take only one parameter."));
            } else {
                try {
                    mapExpressionsConditions.call(1);
                } catch (MissingMethodException e) {
                    addError(new IllegalArgumentException("No Recursions allowed."));
                }
            }
        }

    }

}
