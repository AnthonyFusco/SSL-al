package builders;

import kernel.datasources.executables.simulations.sensors.Sensor;
import kernel.datasources.executables.simulations.sensors.SensorsLot;
import kernel.datasources.laws.DataSource;
import kernel.units.Duration;
import kernel.units.Frequency;
import kernel.units.TimeUnit;

import java.util.ArrayList;
import java.util.List;

public class SensorsLotBuilder extends AbstractEntityBuilder<SensorsLot> {
    private static final int DEFAULT_SENSORS_NUMBER = 1;
    private static final Frequency DEFAULT_FREQUENCY = new Frequency(1, new Duration(1, TimeUnit.Minute));

    private int sensorsNumber = DEFAULT_SENSORS_NUMBER;
    private EntityBuilder<DataSource> builder;
    private Frequency frequency = DEFAULT_FREQUENCY;
    private boolean frequencyDefined = false;
    private boolean numberDefined = false;

    public SensorsLotBuilder(int definitionLine) {
        super(definitionLine);
    }

    public SensorsLotBuilder sensorsNumber(int sensorsNumber) {
        numberDefined = true;
        this.sensorsNumber = sensorsNumber;
        return this;
    }

    public SensorsLotBuilder law(EntityBuilder<DataSource> lawBuilder) {
        this.builder = lawBuilder;
        return this;
    }

    public SensorsLotBuilder frequency(Frequency frequency) {
        frequencyDefined = true;
        this.frequency = frequency;
        return this;
    }

    @Override
    public SensorsLot build() {
        SensorsLot result = new SensorsLot();
        result.setSensors(populateSensors());
        result.setFrequencyValue(frequency.getValue());
        result.setIsExecutable(isExecutable());
        result.setExecutableName(getExecutableName());
        return result;
    }

    private List<Sensor> populateSensors() {
        List<Sensor> sensors = new ArrayList<>();
        for (int i = 0; i < sensorsNumber; i++) {
            DataSource dataSource = builder.build();
            sensors.add(new Sensor(dataSource));
        }
        return sensors;
    }

    @Override
    public void validate() {
        if (!numberDefined) {
            addWarning("no sensor number specified, using default number of " + DEFAULT_SENSORS_NUMBER);
        }
        if (sensorsNumber <= 0) {
            addWarning("bad sensor number specified : " + sensorsNumber + ", using default number of " + DEFAULT_SENSORS_NUMBER);
        }
        if (!frequencyDefined) {
            addWarning("no frequency specified, using default frequency of " + DEFAULT_FREQUENCY);
        }

        if (builder == null) {
            addError(new IllegalArgumentException("No law specified for sensor lot"));
        }
    }
}
