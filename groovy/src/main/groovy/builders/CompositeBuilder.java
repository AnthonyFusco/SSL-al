package builders;

import groovy.lang.Closure;
import groovy.lang.MissingMethodException;
import kernel.structural.EntityBuilder;
import kernel.structural.composite.Composite;
import kernel.units.Frequency;
import kernel.visitor.ExecutableSource;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class CompositeBuilder extends AbstractEntityBuilder<Composite> {
    private List<EntityBuilder<ExecutableSource>> executables;
    private Predicate<? super Double> filterPredicate;
    private Function<Double, Double> mapFunction;
    private BinaryOperator<Double> reduceFunction;
    private Frequency frequency;

    public CompositeBuilder(int definitionLine) {
        super(definitionLine);
    }

    public CompositeBuilder withSensors(List<EntityBuilder<ExecutableSource>> sensors) {
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

    public Composite build() {
        Composite composite = new Composite();
        composite.setFilterPredicate(filterPredicate);
        composite.setMapFunction(mapFunction);
        composite.setReduceFunction(reduceFunction);
        composite.setBuilders(executables);
        composite.setFrequency(frequency);
        composite.setExecutable(isExecutable());
        composite.setExecutableName(getExecutableName());
        return composite;
    }

    @Override
    public void validate() {
        //todo identity for functions
        //check executables are executables or !!!replays!!!
        if (filterPredicate == null) {
            this.filterPredicate = (Predicate<Double>) aDouble -> true;
            addWarning("No predicate function defined, using Identity by default");
        }
        if (mapFunction == null) {
            this.mapFunction = Function.identity();
            addWarning("No map function defined, using Identity by default");
        }
        if (reduceFunction == null) {
            addError(new IllegalArgumentException("Reduce function cannot be null"));
        }
        boolean isNotExecutable = executables.stream().anyMatch(source -> source instanceof LawBuilder);
        if (isNotExecutable) {
            addError(new IllegalArgumentException("All sensors must be either sensor lots, replays or composites"));
        }

    }

}