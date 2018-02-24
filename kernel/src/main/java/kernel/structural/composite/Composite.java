package kernel.structural.composite;

import kernel.Measurement;
import kernel.structural.EntityBuilder;
import kernel.structural.SensorsLot;
import kernel.units.Frequency;
import kernel.visitor.ExecutableSource;
import kernel.visitor.Visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class Composite extends ExecutableSource {
    private String name;
    private Predicate<? super Double> filterPredicate;
    private Function<Double, Double> mapFunction;
    private BinaryOperator<Double> reduceFunction;
    private List<ExecutableSource> executables = new ArrayList<>();
    private List<EntityBuilder<SensorsLot>> builders;
    private Frequency frequency;
    private boolean isExecutable;

    public Composite() {
        this.name = "composite";
    }

    @Override
    public List<Measurement> generateNextMeasurement(double t) {

        populateSensorLots();

        List<Double> values = new ArrayList<>();
        for (ExecutableSource executableSource : executables) {
            List<Measurement> measurements = executableSource.generateNextMeasurement(t);
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

    private void populateSensorLots() {
        if (executables.isEmpty()) {
            builders.forEach(builder -> executables.add(builder.build()));
        }
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
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

    public void setBuilders(List<EntityBuilder<SensorsLot>> builders) {
        this.builders = builders;
    }

    public double getFrequencyValue() {
        return frequency.getValue();
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    @Override
    public boolean isExecutable() {
        return isExecutable;
    }

    public void setExecutable(boolean executable) {
        isExecutable = executable;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
