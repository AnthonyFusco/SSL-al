package builders;

import dsl.SslModel;
import kernel.structural.composite.Composite;
import kernel.units.Frequency;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class CompositeBuilder extends AbstractEntityBuilder<Composite> {
    private String compositeName;
    private List<String> lots;
    private Predicate<? super Double> filterPredicate;
    private Function<Double, Double> mapFunction;
    private BinaryOperator<Double> reduceFunction;
    private Frequency frequency;

    public CompositeBuilder(String compositeName) {
        this.compositeName = compositeName;
    }

    public CompositeBuilder withLots(List<String> lots) {
        this.lots = lots;
        return this;
    }

    public CompositeBuilder filter(Predicate<? super Double> predicate) {
//        List<Double> l = Arrays.asList(1.0, 2.0, 3.0);
//        List<Double> result = l.stream().map(predicate).collect(Collectors.toList());
//        System.out.println(result);
        this.filterPredicate = predicate;
        return this;
    }

    public CompositeBuilder map(Function<Double, Double> map) {
//        List<Double> l = Arrays.asList(1.0, 2.0, 3.0);
//        List<Double> result = l.stream().map(map).collect(Collectors.toList());
//        System.out.println(result);
        this.mapFunction = map;
        return this;
    }

    public CompositeBuilder reduce(BinaryOperator<Double> reduce) {
//        List<Double> l = Arrays.asList(1.0, 2.0, 3.0);
//        Optional<Double> result = l.stream().reduce((aDouble, aDouble2) -> aDouble + aDouble2);
//        System.out.println(result);
        this.reduceFunction = reduce;
        return this;
    }

    public CompositeBuilder withFrequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    public Composite build() {
        Composite composite = new Composite();
        composite.setName(compositeName);
        composite.setFilterPredicate(filterPredicate);
        composite.setMapFunction(mapFunction);
        composite.setReduceFunction(reduceFunction);
        composite.setSensorsLotsNames(lots);
        composite.setFrequency(frequency);
        return composite;
    }

    @Override
    public void validate(SslModel model) {

    }
}
