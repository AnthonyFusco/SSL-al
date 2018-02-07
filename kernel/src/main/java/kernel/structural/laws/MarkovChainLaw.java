package kernel.structural.laws;

import kernel.Measurement;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.unit.objects.Probabilities;

import java.util.*;

public class MarkovChainLaw implements Law {
    private String name;
    private List<List<Double>> matrix;
    private int currState = 0;


    @Override
    public Measurement generateNextMeasurement(double t) {
        MockNeat mockNeat = MockNeat.threadLocal();
        Probabilities<Integer> p = mockNeat.probabilites(Integer.class);


        for(int i = 0 ; i < matrix.size(); i++){
            p.add(getMatrix().get(currState).get(i), i);
        }
        currState = p.val();

        long timestamp = System.currentTimeMillis();
        return new Measurement<>(name, timestamp, currState+"");
    }

    @Override
    public String getName() {
        return name;
    }

    public List<List<Double>> getMatrix() {
        return matrix;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setCurrState(int currState) {
        this.currState = currState;
    }

    public void setMatrix(List<List<Double>> matrix) {
        this.matrix = matrix;
    }
}
