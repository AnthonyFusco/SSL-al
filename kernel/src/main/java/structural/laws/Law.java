package structural.laws;

import kernel.Measurement;
import kernel.NamedElement;
import structural.SensorsLot;

import java.util.List;

public interface Law extends NamedElement {
    List<Measurement> play(SensorsLot lot);
    Law applyLaw(Law law);
}
