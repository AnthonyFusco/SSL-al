package builders;

import kernel.structural.laws.DataSource;

public abstract class LawBuilder<T extends DataSource> extends AbstractEntityBuilder<T> {
    private String name;

    public LawBuilder(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract T build();
}
