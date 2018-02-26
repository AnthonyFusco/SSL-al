package kernel.datasources.executables.simulations.sensors;

import kernel.datasources.Measurement;
import kernel.datasources.NamedElement;
import kernel.datasources.laws.DataSource;

import java.util.List;
import java.util.UUID;

public class Sensor implements DataSource {
    private String name;
    private DataSource dataSource;

    public Sensor(DataSource dataSource) {
        this.dataSource = dataSource;
        this.name = UUID.randomUUID().toString();
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
    public List<Measurement> generateNextMeasurement(double t) {
        List<Measurement> values = dataSource.generateNextMeasurement(t);
        values.forEach(v -> v.setSensorName(name));
        return values;
    }

    @Override
    public boolean isExecutable() {
        return false;
    }
}
