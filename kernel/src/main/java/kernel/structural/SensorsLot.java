package kernel.structural;

import kernel.Measurement;
import kernel.structural.laws.DataSource;
import kernel.visitor.Visitable;
import kernel.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class SensorsLot implements DataSource, Visitable {
    private List<Sensor> sensors;
    private int sensorsNumber;
    private String name;
    private double frequencyValue;
    private EntityBuilder<DataSource> builder;

    public SensorsLot() {
        //ignore
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void generatesSensors() {
        DataSource dataSource = builder.build();
        sensors = new ArrayList<>();
        for (int i = 0; i < sensorsNumber; i++) {
            sensors.add(new Sensor(dataSource));
        }
    }

    @Override
    public List<Measurement> generateNextMeasurement(double t) {
        List<Measurement> measurements = new ArrayList<>();
        for (Sensor sensor : sensors) {
            List<Measurement> measurement = sensor.generateNextMeasurement(t);
            measurements.addAll(measurement);
        }
        return measurements;
    }

    public void setSensorsNumber(int sensorsNumber) {
        this.sensorsNumber = sensorsNumber;
    }

    public double getFrequencyValue() {
        return frequencyValue;
    }

    public void setFrequencyValue(double frequencyValue) {
        this.frequencyValue = frequencyValue;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void setBuilder(EntityBuilder<DataSource> builder) {
        this.builder = builder;
    }

    public EntityBuilder<DataSource> getLawBuilder() {
        return builder;
    }
}
