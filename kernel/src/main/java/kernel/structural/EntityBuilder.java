package kernel.structural;

import kernel.structural.laws.DataSource;

public interface EntityBuilder<T extends DataSource> {
    T build();

    void validate();

    void printErrors();

    boolean isInErrorState();

    void setExecutable(boolean isExecutable);

}
