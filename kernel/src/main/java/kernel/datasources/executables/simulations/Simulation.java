package kernel.datasources.executables.simulations;

import kernel.datasources.executables.ExecutableSource;

public abstract class Simulation extends ExecutableSource {

    public abstract double getFrequencyValue();

    public abstract void setFrequencyValue(double frequencyValue);
}
