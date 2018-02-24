package dsl;

import builders.EntityBuilder;
import groovy.lang.Binding;
import kernel.datasources.laws.DataSource;

import java.util.ArrayList;
import java.util.List;

public class SslModel {
    private List<EntityBuilder<DataSource>> dataSourcesBuilders;

    public SslModel() {
        this.dataSourcesBuilders = new ArrayList<>();
    }

    public void addDataSourcesBuilder(EntityBuilder<DataSource> builder) {
        dataSourcesBuilders.add(builder);
    }

    public List<EntityBuilder<DataSource>> getDataSourcesBuilders() {
        return dataSourcesBuilders;
    }

}
