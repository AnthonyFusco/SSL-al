package kernel.structural.laws;

import kernel.Measurement;
import kernel.NamedElement;
import kernel.structural.SensorsLot;

import java.util.List;

public interface Law extends NamedElement {
    List<Measurement> play(SensorsLot lot);
    Law applyLaw(Law law);
}
