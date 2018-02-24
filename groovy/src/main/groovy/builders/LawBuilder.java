package builders;

import kernel.datasources.laws.DataSource;

public abstract class LawBuilder<T extends DataSource> extends AbstractEntityBuilder<T> {

    public LawBuilder(int definitionLine) {
        super(definitionLine);
    }
}
