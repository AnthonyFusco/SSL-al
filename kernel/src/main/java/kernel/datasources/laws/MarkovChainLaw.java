package kernel.datasources.laws;

import kernel.datasources.Measurement;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.unit.objects.Probabilities;

import java.util.Collections;
import java.util.List;

public class MarkovChainLaw implements DataSource {
    private String name;
    private List<List<Double>> matrix;
    private int currState = 0;
    private double changeStateFrequencyValue;
    private boolean blockComputingNewState = false;
    private double lastTimeCompute = 0;
    private boolean isExecutable;

    public MarkovChainLaw() {
        this.name = "MarkovChain";
    }

    @Override
    public List<Measurement> generateNextMeasurement(double t) {
        double step = 1.0 / changeStateFrequencyValue;
        if (lastTimeCompute + step < t) {
            lastTimeCompute = t;
            blockComputingNewState = false;
        }

        MockNeat mockNeat = MockNeat.threadLocal();
        Probabilities<Integer> p = mockNeat.probabilites(Integer.class);


        for (int i = 0; i < matrix.size(); i++) {
            p.add(getMatrix().get(currState).get(i), i);
        }
        if (!blockComputingNewState) {
            currState = p.val();
            blockComputingNewState = true;
        }

        return Collections.singletonList(new Measurement<>(name, (long) t, currState + ""));
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    private List<List<Double>> getMatrix() {
        return matrix;
    }

    public void setMatrix(List<List<Double>> matrix) {
        this.matrix = matrix;
    }

    public void setCurrState(int currState) {
        this.currState = currState;
    }

    public void setChangeStateFrequencyValue(double changeStateFrequencyValue) {
        this.changeStateFrequencyValue = changeStateFrequencyValue;
    }

    @Override
    public boolean isExecutable() {
        return isExecutable;
    }

    public void setExecutable(boolean executable) {
        isExecutable = executable;
    }
}
