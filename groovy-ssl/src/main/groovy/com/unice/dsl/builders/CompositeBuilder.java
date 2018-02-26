package com.unice.dsl.builders;

import kernel.datasources.executables.PhysicalDataSource;
import kernel.datasources.executables.simulations.composite.Composite;
import kernel.units.Frequency;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CompositeBuilder extends AbstractEntityBuilder<Composite> {
    private List<EntityBuilder<PhysicalDataSource>> executables;
    private Predicate<? super Double> filterPredicate;
    private Function<Double, Double> mapFunction;
    private BinaryOperator<Double> reduceFunction;
    private Frequency frequency;

    public CompositeBuilder(int definitionLine) {
        super(definitionLine);
    }

    public CompositeBuilder withSensors(List<EntityBuilder<PhysicalDataSource>> sensors) {
        this.executables = sensors;
        return this;
    }

    public CompositeBuilder filter(Predicate<? super Double> predicate) {
        this.filterPredicate = predicate;
        return this;
    }

    public CompositeBuilder map(Function<Double, Double> map) {
        this.mapFunction = map;
        return this;
    }

    public CompositeBuilder reduce(BinaryOperator<Double> reduce) {
        this.reduceFunction = reduce;
        return this;
    }

    public CompositeBuilder frequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    @Override
    public Composite build() {
        Composite composite = new Composite();
        composite.setFilterPredicate(filterPredicate);
        composite.setMapFunction(mapFunction);
        composite.setReduceFunction(reduceFunction);
        composite.setExecutables(executables.stream().map(EntityBuilder::build).collect(Collectors.toList()));
        composite.setFrequencyValue(frequency.getValue());
        composite.setIsExecutable(isExecutable());
        composite.setExecutableName(getExecutableName());
        return composite;
    }

    @Override
    public void validate() {
        if (filterPredicate == null) {
            this.filterPredicate = (Predicate<Double>) aDouble -> true;
            addWarning("No predicate function defined, using Identity by default");
        }
        try {
            filterPredicate.test(1.0);
        } catch (Exception e) {
            addError(new IllegalArgumentException("Filter function not applicable. " +
                    "The closure must be of the form { x -> x != 42 }"));
        }
        if (mapFunction == null) {
            this.mapFunction = Function.identity();
            addWarning("No map function defined, using Identity by default");
        }
        try {
            mapFunction.apply(1.0);
        } catch (Exception e) {
            addError(new IllegalArgumentException("Map function not applicable. " +
                    "The closure must be of the form { x -> x * 2 }"));
        }
        if (reduceFunction == null) {
            addError(new IllegalArgumentException("Reduce function cannot be null"));
        }
        try {
            reduceFunction.apply(1.0, 1.0);
        } catch (Exception e) {
            addError(new IllegalArgumentException("Reduce function not applicable. " +
                    "The closure must be of the form { res, sensors -> res + sensors }"));
        }
        boolean isNotExecutable = executables.stream().anyMatch(source -> source instanceof LawBuilder);
        if (isNotExecutable) {
            addError(new IllegalArgumentException("All sensors must be either sensor lots, replays or composites"));
        }
    }

}