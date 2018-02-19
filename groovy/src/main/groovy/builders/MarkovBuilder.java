package builders;

import dsl.SslModel;
import kernel.structural.laws.MarkovChainLaw;
import kernel.units.Duration;
import kernel.units.Frequency;
import kernel.units.TimeUnit;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class MarkovBuilder extends LawBuilder<MarkovChainLaw> {
    private List<List<Double>> matrix;
    private Frequency frequency;

    public MarkovBuilder(String lawName) {
        super(lawName);
    }

    public MarkovBuilder withMatrix(List<List<BigDecimal>> matrix) {
        this.matrix = matrix
                .stream()
                .map(bigDecimals -> bigDecimals.stream()
                        .map(BigDecimal::doubleValue)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        return this;
    }

    public MarkovBuilder changeStateFrequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    @Override
    public MarkovChainLaw build() {
        MarkovChainLaw law = new MarkovChainLaw();
        law.setName(getName());
        law.setMatrix(matrix);
        law.setChangeStateFrequencyValue(frequency.getValue());
        return law;
    }

    @Override
    public void validate(SslModel model) {
        String name = getName();
        if (name == null || name.isEmpty()) {
            addError(new IllegalArgumentException("The name of a Markov Chain must not be empty"));
        }

        if (matrix == null) {
            addError(new IllegalArgumentException("Missing a matrix for the markov chain " + name +
                    ", use the method withMatrix"));
        }

        if (matrix.isEmpty()) {
            addError(new IllegalArgumentException("The matrix of a markov chain cannot be empty.\n" +
                    "Example: withMatrix([[0.3, 0.2, 0.5], [0.15, 0.8, 0.05], [0.25, 0.25, 0.5]])"));
        }

        int firstLineSize = matrix.get(0).size();
        for (List<Double> line : matrix) {
            if (line.size() != firstLineSize) {
                addError(new IllegalArgumentException("The matrix " + name + " isn't square"));
                break;
            }
        }

        boolean isNotSumOfOne = matrix
                .stream()
                .mapToDouble(doubles -> doubles
                        .stream()
                        .mapToDouble(aDouble -> aDouble)
                        .sum())
                .anyMatch(v -> Math.abs(1.0 - v) > Math.ulp(1.0));

        if (isNotSumOfOne) {
            addError(new IllegalArgumentException("All lines of the matrix " + name + " must have a sum of 1"));
        }

        if (frequency == null) {
            System.out.println("\u001B[33mWARNING: no frequency specified on markov chain " + name +
                    ", using default frequency of 1/s\u001B[37m");
            frequency = new Frequency(1, new Duration(1, TimeUnit.Second));
        }

        if (frequency.getValue() <= 0 || frequency.getDuration().getValue() <= 0 || frequency.getOccurrences() <= 0) {
            addError(new IllegalArgumentException("Frequency of the markov chain " + name + " cannot be <= 0\u001B[37m"));
        }
    }
}
