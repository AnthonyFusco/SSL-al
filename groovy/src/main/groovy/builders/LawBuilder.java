package builders;

import kernel.structural.laws.Law;

public abstract class LawBuilder<T extends Law> extends AbstractEntityBuilder<T> {
    private String lawName;

    public LawBuilder(String lawName) {
        this.lawName = lawName;
    }

    public String getLawName() {
        return lawName;
    }


    public abstract T build();
}
