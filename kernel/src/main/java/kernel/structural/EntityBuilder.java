package kernel.structural;

public interface EntityBuilder<T> {
    T build();

    void validate();

    String getName();

    void printErrors();

    boolean isInErrorState();
}
