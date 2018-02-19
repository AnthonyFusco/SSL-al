package kernel.structural.composite;

import kernel.Measurement;
import kernel.structural.Sensor;
import kernel.structural.SensorsLot;
import kernel.structural.laws.DataSource;
import kernel.units.Frequency;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;

public class Composite implements DataSource {
    private String name;
    private Predicate<? super Double> filterPredicate;
    private Function<Double, Double> mapFunction;
    private BinaryOperator<Double> reduceFunction;
    private List<SensorsLot> sensorsLots;
    private List<String> sensorsLotsNames;
    private Frequency frequency;

    @Override
    public List<Measurement> generateNextMeasurement(double t) {
        List<Double> values = new ArrayList<>();
        for (SensorsLot sensorsLot : sensorsLots) {
            List<Measurement> measurements = sensorsLot.generateNextMeasurement(t);
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

    public void setSensorsLots(List<SensorsLot> sensorsLots) {
        this.sensorsLots = sensorsLots;
    }

    public void setSensorsLotsNames(List<String> sensorsLotsNames) {
        this.sensorsLotsNames = sensorsLotsNames;
    }

    public List<String> getSensorsLotsNames() {
        return sensorsLotsNames;
    }

    public void setFrequency(Frequency frequency) {
        this.frequency = frequency;
    }

    public Frequency getFrequency() {
        return frequency;
    }
}
