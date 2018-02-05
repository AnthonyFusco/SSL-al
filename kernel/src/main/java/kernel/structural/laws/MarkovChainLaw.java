package kernel.structural.laws;

import kernel.Measurement;
import kernel.structural.laws.Law;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.unit.objects.Probabilities;

import java.time.Instant;
import java.util.*;

public class MarkovChainLaw implements Law {
    private String name;
    private List<List<Double>> matrix;
    private int currState = 0;

    @Override
    public Measurement generateNextMeasurement(int t) {
        MockNeat mockNeat = MockNeat.threadLocal();
        Probabilities<Integer> p = mockNeat.probabilites(Integer.class);


        for(int i = 0 ; i < matrix.size(); i++){
            p.add(matrix.get(currState).get(i), i);
        }
        currState = p.val();

        long timestamp = System.currentTimeMillis();
        return new Measurement<>(name, timestamp, currState+"");
    }

    @Override
    public String getName() {
        return name;
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
