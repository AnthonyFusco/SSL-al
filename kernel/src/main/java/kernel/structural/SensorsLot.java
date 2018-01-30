package kernel.structural;

import kernel.NamedElement;
import kernel.structural.laws.Law;

import java.util.ArrayList;
import java.util.List;

public class SensorsLot implements NamedElement {
    private List<Sensor> sensors;
    private Law law;
    private int sensorsNumber;
    private String name;
    private int simulationDuration;

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

    public void setSensorsNumber(int sensorsNumber) {
        this.sensorsNumber = sensorsNumber;
    }

    public void setLaw(Law law) {
        this.law = law;
    }

    public Law getLaw() {
        return law;
    }

    public void generatesSensors() {
        sensors = new ArrayList<>();
        for (int i = 0; i < sensorsNumber; i++) {
            sensors.add(new Sensor(law));
        }
    }

    public int getSimulationDuration() {
        return simulationDuration;
    }

    public void setSimulationDuration(int simulationDuration) {
        this.simulationDuration = simulationDuration;
    }
}
