package kernel.structural;

import kernel.NamedElement;
import kernel.structural.laws.Law;

import java.util.ArrayList;
import java.util.List;

public class SensorsLot implements NamedElement {
    private List<Sensor> sensors;
    private int sensorsNumber;
    private String name;
    private String lawName;
    private double frequencyValue;

    public SensorsLot() {

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

    public void generatesSensors(Law law) {
        sensors = new ArrayList<>();
        for (int i = 0; i < sensorsNumber; i++) {
            sensors.add(new Sensor(law));
        }
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

    public void setFrequencyValue(double frequencyValue) {
        this.frequencyValue = frequencyValue;
    }

    public double getFrequencyValue() {
        return frequencyValue;
    }
}
