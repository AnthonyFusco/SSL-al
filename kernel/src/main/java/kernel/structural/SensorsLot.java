package kernel.structural;

import kernel.NamedElement;
import kernel.structural.laws.Law;

import java.util.ArrayList;
import java.util.List;

public class SensorsLot implements NamedElement {
    private List<Sensor> sensors;
    private int sensorsNumber;
    private String name;
    private int simulationDuration;

    public SensorsLot(String name, int sensorsNumber, Law law) {
        this.name = name;
        this.sensorsNumber = sensorsNumber;
        this.sensors = generatesSensors(law);
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

    private List<Sensor> generatesSensors(Law law) {
        List<Sensor> sensors = new ArrayList<>();
        for (int i = 0; i < sensorsNumber; i++) {
            sensors.add(new Sensor(law));
        }
        return sensors;
    }

    public void setSimulationDuration(int simulationDuration) {
        this.simulationDuration = simulationDuration;
    }

    public int getSimulationDuration() {
        return simulationDuration;
    }
}
