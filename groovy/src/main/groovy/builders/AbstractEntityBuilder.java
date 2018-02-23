package builders;

import dsl.Runner;
import kernel.structural.EntityBuilder;
import kernel.structural.laws.DataSource;

import java.util.Stack;

public abstract class AbstractEntityBuilder<T extends DataSource> implements EntityBuilder<T> {
    private Stack<Exception> exceptions = new Stack<>();
    private Stack<String> warnings = new Stack<>();
    private boolean isExecutable;
    private int definitionLine;
    private String executableName = "";

    public AbstractEntityBuilder(int definitionLine) {
        this.definitionLine = definitionLine;
    }

    public void addError(Exception e) {
        exceptions.push(e);
    }

    public void addWarning(String message) {
        warnings.push(message);
    }

    public boolean isInErrorState() {
        return !exceptions.empty();
    }

    public void printWarningsErrors() {
        for (String warning : warnings) {
            System.out.println("\u001B[33mWARNING: " + warning + " " + getErrorLocation() + "\u001B[37m");
        }
        for (Exception exception : exceptions) {
            System.out.println("\u001B[31mERROR: " + exception.getMessage() + " " + getErrorLocation() + "\u001B[37m");
        }
        if (!exceptions.empty()) {
            System.out.println("\n");
        }
    }

    public String getErrorLocation() {
        return ".(" + Runner.currentFile + ":" + definitionLine + ")";
    }

    public abstract T build();

    boolean isExecutable() {
        return isExecutable;
    }

    @Override
    public void setExecutable(boolean executable) {
        isExecutable = executable;
    }

    public int getDefinitionLine() {
        return definitionLine;
    }

    String getExecutableName() {
        return executableName;
    }

    @Override
    public void setExecutableName(String name) {
        this.executableName = name;
    }
}
