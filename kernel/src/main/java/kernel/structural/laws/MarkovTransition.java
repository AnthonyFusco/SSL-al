package kernel.structural.laws;

public class MarkovTransition {
    private double probability;
    private String targetState;

    public MarkovTransition(String targetState, double probability) {
        this.probability = probability;
        this.targetState = targetState;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }

    public String getTargetState() {
        return targetState;
    }

    public void setTargetState(String targetState) {
        this.targetState = targetState;
    }
}
