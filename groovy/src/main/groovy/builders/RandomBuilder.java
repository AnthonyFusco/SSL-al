package builders;

import dsl.SslModel;
import kernel.structural.laws.RandomLaw;

import java.util.List;

public class RandomBuilder extends LawBuilder<RandomLaw> {

    private List<Integer> bornes;

    public RandomBuilder(String lawName) {
        super(lawName);
    }

    @Override
    public RandomLaw build() {
        RandomLaw law = new RandomLaw();
        law.setName(this.getName());
        law.setBorneInf(bornes.get(0));
        law.setBorneSup(bornes.get(1));
        return law;
    }

    public RandomBuilder withinRange(List<Integer> range) {
        this.bornes = range;
        return this;
    }

    @Override
    public void validate(SslModel model) {
        String name = this.getName();
        if (name == null || name.isEmpty()) {
            addError(new IllegalArgumentException("The name of a Random law must not be empty"));
        }

        if (bornes == null) {
            addError(new IllegalArgumentException("You must declare a non-empty range interval"));

        }
        if (bornes.size() != 2) {
            addError(new IllegalArgumentException("You must declare a good range interval ex:([ 0, 10])"));
        }


    }

    @Override
    public String getName() {
        return super.getName();
    }
}
