package kernel.datasources.laws;

import kernel.datasources.Measurement;
import kernel.datasources.NamedElement;

import java.util.List;

public interface DataSource extends NamedElement {
    List<Measurement> generateNextMeasurement(double t);

    boolean isExecutable();
}
