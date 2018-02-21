package builders;

import kernel.structural.EntityBuilder;
import kernel.structural.SensorsLot;
import kernel.structural.composite.Composite;
import kernel.structural.laws.DataSource;
import kernel.units.Frequency;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class CompositeBuilder extends AbstractEntityBuilder<Composite> {
    private String name;
    private List<EntityBuilder<SensorsLot>> lots;
    private Predicate<? super Double> filterPredicate;
    private Function<Double, Double> mapFunction;
    private BinaryOperator<Double> reduceFunction;
    private Frequency frequency;

    public CompositeBuilder() {
        this.name = "composite";
    }

    @Override
    public String getName() {
        return name;
    }

    public CompositeBuilder withLots(List<EntityBuilder<SensorsLot>> lots) {
        this.lots = lots;
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

    public Composite build() {
        Composite composite = new Composite();
        composite.setName(name);
        composite.setFilterPredicate(filterPredicate);
        composite.setMapFunction(mapFunction);
        composite.setReduceFunction(reduceFunction);
        composite.setBuilders(lots);
        composite.setFrequency(frequency);
        return composite;
    }

    @Override
    public void validate() {

    }
}
