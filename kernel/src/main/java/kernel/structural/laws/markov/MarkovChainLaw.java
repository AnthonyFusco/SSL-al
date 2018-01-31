package kernel.structural.laws.markov;

import kernel.Measurement;
import kernel.structural.laws.Law;

import java.time.Instant;
import java.util.*;

public class MarkovChainLaw implements Law {
    private String name;
    private List<String> states = new ArrayList<>();
    private String currentState;
    private Map<String, MarkovTransition> transitions = new HashMap<>();

    @Override
    public Measurement generateNextMeasurement(int t) {
        Random r = new Random();
        MarkovTransition mt = transitions.get(currentState);
        if (r.nextFloat() >= mt.getProbability()) {
            currentState = mt.getTargetState();
        }

        long timestamp = System.currentTimeMillis();
        return new Measurement<>(name, timestamp, currentState);
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
}
