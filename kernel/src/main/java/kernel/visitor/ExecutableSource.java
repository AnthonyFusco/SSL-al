package kernel.visitor;

import kernel.structural.laws.DataSource;

public abstract class ExecutableSource implements Visitable, DataSource {
    private String executableName = "";

    public abstract double getFrequencyValue();

    public String getExecutableName() {
        return executableName;
    }

    public void setExecutableName(String executableName) {
        this.executableName = executableName;
    }
}
