package builders;

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

    public MarkovBuilder(int definitionLine) {
        super(definitionLine);
    }

    public MarkovBuilder matrix(List<List<BigDecimal>> matrix) {
        this.matrix = matrix
                .stream()
                .map(bigDecimals -> bigDecimals.stream()
                        .map(BigDecimal::doubleValue)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        return this;
    }

    public MarkovBuilder stateFrequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    @Override
    public MarkovChainLaw build() {
        MarkovChainLaw law = new MarkovChainLaw();
        law.setMatrix(matrix);
        law.setChangeStateFrequencyValue(frequency.getValue());
        law.setExecutable(isExecutable());
        return law;
    }

    @Override
    public void validate() {
        if (matrix == null) {
            addError(new IllegalArgumentException("Missing a matrix for the markov chain at" + getErrorLocation() +
                    ", use the method matrix"));
        }

        if (matrix.isEmpty()) {
            addError(new IllegalArgumentException("The matrix of a markov chain cannot be empty.\n" +
                    "Example: matrix([[0.3, 0.2, 0.5], [0.15, 0.8, 0.05], [0.25, 0.25, 0.5]])"));
        }

        int firstLineSize = matrix.get(0).size();
        for (List<Double> line : matrix) {
            if (line.size() != firstLineSize) {
                addError(new IllegalArgumentException("The matrix at " + getErrorLocation() + " isn't square"));
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
            addError(new IllegalArgumentException("All lines of the matrix at " + getErrorLocation() + " must have a sum of 1"));
        }

        if (frequency == null) {
            System.out.println("\u001B[33mWARNING: no frequency specified on markov chain at " + getErrorLocation() +
                    ", using default frequency of 1/s\u001B[37m");
            frequency = new Frequency(1, new Duration(1, TimeUnit.Second));
        }

        if (frequency.getValue() <= 0 || frequency.getDuration().getValue() <= 0 || frequency.getOccurrences() <= 0) {
            addError(new IllegalArgumentException("Frequency of the markov chain at " + getErrorLocation() + " cannot be <= 0\u001B[37m"));
        }
    }
}
