package kernel.visitor;

import kernel.structural.laws.DataSource;
import kernel.units.Frequency;

public abstract class ExecutableSource implements Visitable, DataSource {

    public abstract double getFrequencyValue();
}
