package kernel.structural;

import kernel.Measurement;
import kernel.NamedElement;
import kernel.structural.laws.DataSource;

import java.util.List;
import java.util.UUID;

public class Sensor implements NamedElement {
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

    public List<Measurement> generateNextMeasurement(double t) {
        List<Measurement> values = dataSource.generateNextMeasurement(t);
        values.forEach(v -> v.setSensorName(name));
        return values;
    }
}
