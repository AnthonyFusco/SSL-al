package dsl;

import kernel.structural.EntityBuilder;
import groovy.lang.Binding;
import kernel.structural.laws.DataSource;

import java.util.ArrayList;
import java.util.List;

public class SslModel {
    private List<EntityBuilder<DataSource>> dataSourcesBuilders;

    private Binding binding;

    public SslModel(Binding binding) {
        this.binding = binding;
        this.dataSourcesBuilders = new ArrayList<>();
    }

    public void addDataSourcesBuilder(EntityBuilder<DataSource> builder) {
        dataSourcesBuilders.add(builder);
    }

    public List<EntityBuilder<DataSource>> getDataSourcesBuilders() {
        return dataSourcesBuilders;
    }

}
