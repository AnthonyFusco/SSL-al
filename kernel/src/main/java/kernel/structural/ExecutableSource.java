package kernel.structural;

import kernel.structural.laws.DataSource;
import kernel.visitor.Visitable;

public abstract class ExecutableSource implements Visitable, DataSource {
    private String executableName = "";

    public String getExecutableName() {
        return executableName;
    }

    public void setExecutableName(String executableName) {
        this.executableName = executableName;
    }
}
