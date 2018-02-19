package dsl;

import builders.*;
import groovy.lang.Binding;
import kernel.structural.laws.DataSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SslModel {
    private List<EntityBuilder<DataSource>> dataSourcesBuilders;

    private Binding binding;

    public SslModel(Binding binding) {
        this.binding = binding;
        dataSourcesBuilders = new ArrayList<>();
    }

    public void addDataSourcesBuilder(EntityBuilder<DataSource> builder) {
        if (builder.getName() == null || builder.getName().isEmpty()) {
            throw new IllegalArgumentException("A law name cannot be null or empty");
        }
        if (getDataSourcesNames().contains(builder.getName())) {
            throw new IllegalArgumentException("The name " + builder.getName() + " is already taken");
        }
        dataSourcesBuilders.add(builder);
    }

    public List<EntityBuilder<DataSource>> getDataSourcesBuilders() {
        return dataSourcesBuilders;
    }

    public List<String> getDataSourcesNames() {
        return dataSourcesBuilders.stream().map(EntityBuilder::getName).collect(Collectors.toList());
    }

}
