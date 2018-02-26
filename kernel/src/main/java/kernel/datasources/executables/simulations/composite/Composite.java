package kernel.datasources.executables.simulations.composite;

import kernel.datasources.Measurement;
import kernel.datasources.executables.PhysicalDataSource;
import kernel.datasources.executables.simulations.Simulation;
import kernel.visitor.Visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class Composite extends Simulation {
    private String name;
    private Predicate<? super Double> filterPredicate;
    private Function<Double, Double> mapFunction;
    private BinaryOperator<Double> reduceFunction;
    private List<PhysicalDataSource> executables = new ArrayList<>();
    private double frequency;

    public Composite() {
        this.name = "composite";
    }

    @Override
    public List<Measurement> generateNextMeasurement(double t) {
        List<Double> values = new ArrayList<>();
        for (PhysicalDataSource physicalDataSource : executables) {
            List<Measurement> measurements = physicalDataSource.generateNextMeasurement(t);
            for (Measurement measurement : measurements) {
                values.add(Double.valueOf(measurement.getValue().toString()));
            }
        }

        Optional<Double> value = values.stream().filter(filterPredicate).map(mapFunction).reduce(reduceFunction);

        if (!value.isPresent()) {
            System.out.println("NULL VALUE IN COMPOSITE " + name);
            return null;
        }

        return Collections.singletonList(new Measurement<>(name, (long) t, value.get()));
    }

    public void setFilterPredicate(Predicate<? super Double> filterPredicate) {
        this.filterPredicate = filterPredicate;
    }

    public void setMapFunction(Function<Double, Double> mapFunction) {
        this.mapFunction = mapFunction;
    }

    public void setReduceFunction(BinaryOperator<Double> reduceFunction) {
        this.reduceFunction = reduceFunction;
    }

    public void setExecutables(List<PhysicalDataSource> executables) {
        this.executables = executables;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public double getFrequencyValue() {
        return frequency;
    }

    @Override
    public void setFrequencyValue(double frequency) {
        this.frequency = frequency;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
