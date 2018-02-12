package builders;

import dsl.SslModel;
import kernel.structural.SensorsLot;
import kernel.units.Duration;
import kernel.units.Frequency;
import kernel.units.TimeUnit;

public class SensorsLotBuilder implements EntityBuilder<SensorsLot> {
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

    public SensorsLotBuilder withLaw(String lawName) {
        this.lawName = lawName;
        return this;
    }

    public SensorsLotBuilder withFrequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
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
        String name = this.name;
        if(name == null || name.isEmpty()){
            throw new IllegalArgumentException("The name of a Sensorlot must not be empty");
        }

        if (sensorsNumber < 1){
            throw new IllegalArgumentException("The sensorLot" + name + "must contains at least 1 sensor");
        }

        if (frequency == null) {
            System.out.println("WARNING: no frequency specified on SensorLot " + name +
                    ", using default frequency of 1/s");
            frequency = new Frequency(1, new Duration(1, TimeUnit.Second));
        }


    }
}
