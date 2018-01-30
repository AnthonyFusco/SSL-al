package kernel.structural.laws;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MarkovChainLaw implements Law {
    private String name;
    private List<String> states = new ArrayList<>();
    private String initialState;
    private Map<String, MarkovTransition> transitions = new HashMap<>();



    @Override
    public Object generateNextValue(int t) {
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
