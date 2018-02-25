package com.unice.dsl.builders;

import kernel.datasources.laws.RandomLaw;

import java.util.Arrays;
import java.util.List;

public class RandomBuilder extends LawBuilder<RandomLaw> {
    private static final List<Integer> DEFAULT_RANGE = Arrays.asList(0, 10);
    private boolean rangeDeclared = false;
    private List<Integer> range = DEFAULT_RANGE;

    public RandomBuilder(int definitionLine) {
        super(definitionLine);
    }

    @Override
    public RandomLaw build() {
        RandomLaw law = new RandomLaw();
        law.setBorneInf(range.get(0));
        law.setBorneSup(range.get(1));
        law.setExecutable(isExecutable());
        return law;
    }

    public RandomBuilder range(List<Integer> range) {
        this.range = range;
        rangeDeclared = true;
        return this;
    }

    @Override
    public void validate() {
        if (!rangeDeclared) {
            addWarning("no range declared, using default [" + DEFAULT_RANGE + "]");
        }
        if (range.size() != 2) {
            addError(new IllegalArgumentException("You must declare a good range interval ex:([" + DEFAULT_RANGE + "])"));
        }

        if (range.get(0) > range.get(1)) {
            addWarning("Your lower limit is greater than your upper limit, unexpected result");
        }
    }

}
