package kernel.visitor;

import kernel.structural.laws.DataSource;

public abstract class ExecutableSource implements Visitable, DataSource {

    public abstract double getFrequencyValue();
}
