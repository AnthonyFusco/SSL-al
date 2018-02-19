package builders;

import dsl.SslModel;

public interface EntityBuilder<T> {
    T build();

    void validate(SslModel model);

    String getName();

    void printErrors();

    boolean isInErrorState();
}
