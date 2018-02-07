package builders;

import dsl.SslModel;
import kernel.structural.laws.MarkovChainLaw;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MarkovBuilder extends LawBuilder<MarkovChainLaw>{
    private List<List<Double>> matrix;

    public MarkovBuilder(String lawName) {
        super(lawName);
    }

    public MarkovBuilder givenMatrix(List<List<BigDecimal>> matrix) {
        List<List<Double>> tmp = new ArrayList<>();
        for (List<BigDecimal> lbd : matrix){
            List<Double> ls = new ArrayList<>();
            for (BigDecimal bd : lbd){
                ls.add(bd.doubleValue());
            }
            tmp.add(ls);
        }
        this.matrix = tmp;
        return this;
    }



    @Override
    public MarkovChainLaw build() {
        MarkovChainLaw law = new MarkovChainLaw();
        law.setName(getLawName());
        law.setMatrix(matrix);
        return law;
    }

    @Override
    public void validate(SslModel model) {

    }
}
