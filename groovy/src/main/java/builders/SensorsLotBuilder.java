package builders;

import dsl.SslModel;
import kernel.structural.SensorsLot;

public class SensorsLotBuilder implements EntityBuilder<SensorsLot> {
    private int sensorsNumber;
    private String lawName;
    private int duration;
    private String name;

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

    public SensorsLotBuilder withDuration(int duration) {
        this.duration = duration;
        return this;
    }

    @Override
    public SensorsLot build() {
        SensorsLot result = new SensorsLot();
        result.setName(name);
        result.setSimulationDuration(duration);
        result.setLawName(lawName);
        return result;
    }

    @Override
    public void validate(SslModel model) {

    }
}