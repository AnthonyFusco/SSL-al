package kernel.datasources.executables.simulations.sensors;

import kernel.datasources.Measurement;
import kernel.datasources.executables.simulations.Simulation;
import kernel.visitor.Visitor;

import java.util.ArrayList;
import java.util.List;

public class SensorsLot extends Simulation {
    private List<Sensor> sensors = new ArrayList<>();
    private String name;
    private double frequencyValue;

    public SensorsLot() {
        this.name = "SensorLot";
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

    public void setSensors(List<Sensor> sensors) {
        this.sensors = sensors;
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
        return frequencyValue;
    }

    @Override
    public void setFrequencyValue(double frequencyValue) {
        this.frequencyValue = frequencyValue;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

}
