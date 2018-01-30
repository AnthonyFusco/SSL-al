package kernel.structural.laws;

import kernel.Measurement;

import java.util.*;

public class MarkovChainLaw implements Law {
    private String name;
    private List<String> states = new ArrayList<>();
    private String initialState;
    private String currentState;
    private Map<String, MarkovTransition> transitions = new HashMap<>();

    @Override
    public Measurement generateNextMeasurement(int t) {
        Random r = new Random();
        MarkovTransition mt = transitions.get(initialState);
        if (r.nextFloat() < mt.getProbability()){
            initialState = initialState;
        }else{
            initialState = mt.getTargetState();
        }

        Measurement m = new Measurement("name",t+"",initialState);
        return null;
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
}
