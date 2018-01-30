package kernel.structural.laws;

import kernel.Measurement;
import kernel.NamedElement;

public interface Law extends NamedElement {
    Measurement generateNextMeasurement(int t);
}
