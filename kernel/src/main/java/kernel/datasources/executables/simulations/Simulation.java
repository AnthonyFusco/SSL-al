package kernel.datasources.executables.simulations;

import kernel.datasources.executables.PhysicalDataSource;

public abstract class Simulation extends PhysicalDataSource {

    public abstract double getFrequencyValue();

    public abstract void setFrequencyValue(double frequencyValue);
}
