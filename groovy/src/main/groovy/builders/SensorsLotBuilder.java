package builders;

import dsl.SslModel;
import kernel.structural.SensorsLot;
import kernel.units.Duration;
import kernel.units.Frequency;
import kernel.units.TimeUnit;

public class SensorsLotBuilder extends AbstractEntityBuilder<SensorsLot> {
    private int sensorsNumber;
    private String lawName;
    private String name;
    private Frequency frequency;

    public SensorsLotBuilder(String name) {
        this.name = name;
    }

    public SensorsLotBuilder sensorsNumber(int sensorsNumber) {
        this.sensorsNumber = sensorsNumber;
        return this;
    }

    public SensorsLotBuilder law(String lawName) {
        this.lawName = lawName;
        return this;
    }

    public SensorsLotBuilder law(LawBuilder lawBuilder) {
        this.lawName = lawBuilder.getName();
        return this;
    }

    public SensorsLotBuilder frequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    public String getName() {
        return name;
    }

    @Override
    public SensorsLot build() {
        SensorsLot result = new SensorsLot();
        result.setName(name);
        result.setLawName(lawName);
        result.setSensorsNumber(sensorsNumber);
        result.setFrequencyValue(frequency.getValue());
        return result;
    }

    @Override
    public void validate(SslModel model) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("The name of a SensorLot must not be empty");
        }

        if (sensorsNumber <= 0) {
            System.out.println("\u001B[33mWARNING: no sensor number specified on sensor lot " + name +
                    ", using default number of 1\u001B[37m");
            sensorsNumber = 1;
        }

        if (frequency == null) {
            System.out.println("\u001B[33mWARNING: no frequency specified on sensor lot " + name +
                    ", using default frequency of 1/s\u001B[37m");
            frequency = new Frequency(1, new Duration(1, TimeUnit.Second));
        }

        if (lawName == null || lawName.isEmpty()) {
            throw new IllegalArgumentException("The SensorLot " + name + " must have a non empty law name");
        }

        if (!model.getDataSourcesNames().contains(lawName)) {
            throw new IllegalArgumentException("The law of the sensorLot " + name + " does not exist");
        }
    }
}
