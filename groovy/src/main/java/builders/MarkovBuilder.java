package builders;

import dsl.SslModel;
import kernel.structural.laws.MarkovChainLaw;

import java.util.List;

public class MarkovBuilder extends LawBuilder<MarkovChainLaw>{
    private List<List<Double>> matrix;

    public MarkovBuilder(String lawName) {
        super(lawName);
    }

    public MarkovBuilder givenMatrix(List<List<Double>> matrix) {
        this.matrix = matrix;
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
