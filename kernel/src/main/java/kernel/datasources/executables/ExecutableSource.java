package kernel.datasources.executables;

import kernel.datasources.laws.DataSource;
import kernel.visitor.Visitable;

public abstract class ExecutableSource implements Visitable, DataSource {
    private String executableName = "";
    private boolean isExecutable = false;

    public String getExecutableName() {
        return executableName;
    }

    public void setExecutableName(String executableName) {
        this.executableName = executableName;
    }

    @Override
    public boolean isExecutable() {
        return isExecutable;
    }

    public void setIsExecutable(boolean isExecutable) {
        this.isExecutable = isExecutable;
    }
}
