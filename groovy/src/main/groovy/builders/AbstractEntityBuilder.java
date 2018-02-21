package builders;

import dsl.Runner;
import kernel.structural.EntityBuilder;
import kernel.structural.laws.DataSource;

import java.util.Stack;

public abstract class AbstractEntityBuilder<T extends DataSource> implements EntityBuilder<T> {
    private Stack<Exception> exceptions = new Stack<>();
    private boolean isExecutable;
    private int definitionLine;

    public AbstractEntityBuilder(int definitionLine) {
        this.definitionLine = definitionLine;
    }

    void addError(Exception e) {
        exceptions.push(e);
    }

    public boolean isInErrorState() {
        return !exceptions.empty();
    }

    public void printErrors() {
        for (Exception exception : exceptions) {
            System.out.println("\u001B[31mERROR: " + exception.getMessage() + "\u001B[37m");
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

    public int getDefinitionLine() {
        return definitionLine;
    }

    @Override
    public void setExecutable(boolean executable) {
        isExecutable = executable;
    }
}
