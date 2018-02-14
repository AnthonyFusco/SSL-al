package builders;

import java.util.Stack;

public abstract class AbstractEntityBuilder<T> implements EntityBuilder<T> {
    private Stack<Exception> exceptions = new Stack<>();

    public void addError(Exception e) {
        exceptions.push(e);
    }

    public boolean isInErrorState() {
        return !exceptions.empty();
    }

    public void printErrors() {
        for (Exception exception : exceptions) {
            System.out.println("\u001B[31mERROR: " + exception.getMessage() + "\u001B[37m");
        }
        System.out.println("\n");
    }

    public abstract T build();

}
