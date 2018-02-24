package builders;

import kernel.datasources.laws.DataSource;

public interface EntityBuilder<T extends DataSource> {
    T build();

    void validate();

    void printWarningsErrors();

    boolean isInErrorState();

    void setExecutable(boolean isExecutable);

    void setExecutableName(String name);
}
