package kernel.structural.laws;

import kernel.Measurement;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.types.enums.RandomType;
import net.andreinc.mockneat.unit.objects.Probabilities;

import java.util.*;

public class MarkovChainLaw implements Law {
    private String name;
    private List<List<Double>> matrix;
    private int currState = 0;
    private double changeStateFrequencyValue;

    private boolean blockComputingNewState = false;
    private double lastTimeCompute = 0;
    Random r = new Random();



    @Override
    public Measurement generateNextMeasurement(double t) {
        double step = 1.0 / changeStateFrequencyValue;
        if (lastTimeCompute + step < t) {
            lastTimeCompute = t;
            blockComputingNewState = false;
        }

        int valeur = -9999;
        List<Double> tmp = getMatrix().get(currState);
        double piece = r.nextDouble();
        for(int i = 0; i < tmp.size(); i++){
            piece = r.nextDouble();
            if(i == tmp.size() -1){
                valeur = i;
            }else{
                if(piece < tmp.get(i)){
                    valeur = i;
                    break;
                }
            }
        }

        /*if (!blockComputingNewState) {
            currState = valeur;
            blockComputingNewState = true;
        }*/
        currState = valeur;
        return new Measurement<>(name, (long) t, currState + "");
    }

    @Override
    public String getName() {
        return name;
    }

    private List<List<Double>> getMatrix() {
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

    public void setChangeStateFrequencyValue(double changeStateFrequencyValue) {
        this.changeStateFrequencyValue = changeStateFrequencyValue;
    }
}
