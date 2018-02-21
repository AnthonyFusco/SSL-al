package builders;

import kernel.structural.EntityBuilder;
import kernel.structural.laws.DataSource;

import java.util.Stack;

public abstract class AbstractEntityBuilder<T extends DataSource> implements EntityBuilder<T> {
    private Stack<Exception> exceptions = new Stack<>();
    private boolean isExecutable;

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

    public abstract T build();

    boolean isExecutable() {
        return isExecutable;
    }

    @Override
    public void setExecutable(boolean executable) {
        isExecutable = executable;
    }
}
