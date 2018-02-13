package builders;

import dsl.SslModel;
import kernel.structural.laws.RandomLaw;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RandomBuilder extends LawBuilder<RandomLaw> {

    private List<Integer> bornes;

    public RandomBuilder(String lawName) {
        super(lawName);
    }

    @Override
    public RandomLaw build() {
        RandomLaw law = new RandomLaw();
        law.setName(getLawName());
        law.setBorneinf(bornes.get(0));
        law.setBornesup(bornes.get(1));
        return law;
    }

    public RandomBuilder withinRange(List<Integer> range){
        System.out.println("RANGE -> " + range);
        this.bornes = range;
        System.out.println("BORNE -> " + this.bornes);

        return this;
    }

    @Override
    public void validate(SslModel model) {
        String name = getLawName();
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("The name of a Random law must not be empty");
        }

        if(bornes == null){
            throw new IllegalArgumentException("You must declare a non-empty range interval");

        }
        if(bornes.size() != 2){
            throw new IllegalArgumentException("You must declare a good range interval ex:([ 0, 10])");
        }


    }
}
