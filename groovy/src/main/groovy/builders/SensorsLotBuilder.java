package builders;

import kernel.structural.EntityBuilder;
import kernel.structural.SensorsLot;
import kernel.structural.laws.DataSource;
import kernel.units.Duration;
import kernel.units.Frequency;
import kernel.units.TimeUnit;

public class SensorsLotBuilder extends AbstractEntityBuilder<SensorsLot> {
    private int sensorsNumber;
    private EntityBuilder<DataSource> builder;
    private Frequency frequency;

    public SensorsLotBuilder(int definitionLine) {
        super(definitionLine);
    }

    public SensorsLotBuilder sensorsNumber(int sensorsNumber) {
        this.sensorsNumber = sensorsNumber;
        return this;
    }

    public SensorsLotBuilder law(EntityBuilder<DataSource> lawBuilder) {
        this.builder = lawBuilder;
        return this;
    }

    public SensorsLotBuilder frequency(Frequency frequency) {
        this.frequency = frequency;
        return this;
    }

    @Override
    public SensorsLot build() {
        SensorsLot result = new SensorsLot();
        result.setBuilder(builder);
        result.setSensorsNumber(sensorsNumber);
        result.setFrequencyValue(frequency.getValue());
        result.setExecutable(isExecutable());
        return result;
    }

    @Override
    public void validate() {
        if (sensorsNumber <= 0) {
            System.out.println("\u001B[33mWARNING: no sensor number specified on sensor lot " + /*name +*/
                    ", using default number of 1\u001B[37m");
            sensorsNumber = 1;
        }
        if (frequency == null) {
            System.out.println("\u001B[33mWARNING: no frequency specified on sensor lot " + /*name +*/
                    ", using default frequency of 1/min\u001B[37m");
            frequency = new Frequency(1, new Duration(1, TimeUnit.Minute));
        }
    }
}
