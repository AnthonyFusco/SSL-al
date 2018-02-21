package kernel.structural;

import kernel.Measurement;
import kernel.structural.laws.DataSource;
import kernel.visitor.ExecutableSource;
import kernel.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class SensorsLot extends ExecutableSource {
    private List<Sensor> sensors = new ArrayList<>();
    private int sensorsNumber;
    private String name;
    private double frequencyValue;
    private EntityBuilder<DataSource> builder;
    private boolean isExecutable;

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

    private void populateSensors() {
        if (sensors.isEmpty()) {
            DataSource dataSource = builder.build();
            for (int i = 0; i < sensorsNumber; i++) {
                sensors.add(new Sensor(dataSource));
            }
        }
    }

    @Override
    public List<Measurement> generateNextMeasurement(double t) {
        populateSensors();
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

    @Override
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

    @Override
    public boolean isExecutable() {
        return isExecutable;
    }

    public void setExecutable(boolean executable) {
        isExecutable = executable;
    }
}
