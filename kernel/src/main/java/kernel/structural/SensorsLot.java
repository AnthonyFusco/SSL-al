package kernel.structural;

import kernel.Measurement;
import kernel.structural.laws.DataSource;

import java.util.ArrayList;
import java.util.List;

public class SensorsLot implements DataSource {
    private List<Sensor> sensors;
    private int sensorsNumber;
    private String name;
    private String lawName;
    private double frequencyValue;

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

    public void generatesSensors(DataSource dataSource) {
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

    public String getLawName() {
        return lawName;
    }

    public void setLawName(String lawName) {
        this.lawName = lawName;
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
}
