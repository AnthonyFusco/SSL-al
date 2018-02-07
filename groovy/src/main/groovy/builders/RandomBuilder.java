package builders;

import dsl.SslModel;
import kernel.structural.laws.RandomLaw;

public class RandomBuilder extends LawBuilder<RandomLaw> {

    public RandomBuilder(String lawName) {
        super(lawName);
    }

    @Override
    public RandomLaw build() {
        RandomLaw law = new RandomLaw();
        law.setName(getLawName());
        return law;
    }

    @Override
    public void validate(SslModel model) {

    }
}
