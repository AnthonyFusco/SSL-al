package kernel.structural.laws.markov;

import kernel.Measurement;
import kernel.structural.laws.Law;
import net.andreinc.mockneat.MockNeat;
import net.andreinc.mockneat.unit.objects.Probabilities;

import java.time.Instant;
import java.util.*;

public class MarkovChainLaw implements Law {
    private String name;
    private List<String> states = new ArrayList<>();
    private String currentState;
    private Map<String, MarkovTransition> transitions = new HashMap<>();


    /*

     */

    //Prend une matrice en input
    private Double [][] matrice;
    //On set le current state a 0, soit la premiere ligne de la matrice
    private int currState = 0;
    // On set la taille de la matrice (qui doit etre carree).
    private int tailleMatrice;

    @Override
    public Measurement generateNextMeasurement(int t) {
        MockNeat mockNeat = MockNeat.threadLocal();
        Probabilities p = mockNeat.probabilites(Integer.class);

        for(int i = 0 ; i < tailleMatrice; i++){
            p.add(matrice[currState][i],i);
        }
        currState = (Integer)p.val();

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

    public void declareState(String name) {
        states.add(name);
    }

    public void addTransition(String originState, String targetState, double p) {
        transitions.put(originState, new MarkovTransition(targetState, p));
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public void setTransitions(Map<String, MarkovTransition> transitions) {
        this.transitions = transitions;
    }

    public void setCurrState(int currState) {
        this.currState = currState;
    }

    public void setMatrice(Double[][] matrice) {
        this.matrice = matrice;
    }

    public void setTailleMatrice(int tailleMatrice) {
        this.tailleMatrice = tailleMatrice;
        this.matrice = new Double[tailleMatrice][tailleMatrice];
    }
}
