package com.unice.dsl.engine

import com.unice.dsl.builders.EntityBuilder
import kernel.datasources.laws.DataSource

final class SslModel {
    private List<EntityBuilder<DataSource>> dataSourcesBuilders

    SslModel() {
        this.dataSourcesBuilders = new ArrayList<>()
    }

    void addDataSourcesBuilder(EntityBuilder<DataSource> builder) {
        dataSourcesBuilders.add(builder)
    }

    List<EntityBuilder<DataSource>> getDataSourcesBuilders() {
        return dataSourcesBuilders
    }

}
