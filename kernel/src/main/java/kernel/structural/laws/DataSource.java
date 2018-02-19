package kernel.structural.laws;

import kernel.Measurement;
import kernel.NamedElement;

import java.util.List;

public interface DataSource extends NamedElement {
    List<Measurement> generateNextMeasurement(double t);
}
