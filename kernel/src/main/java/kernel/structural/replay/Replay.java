package kernel.structural.replay;

import kernel.Measurement;
import kernel.NamedElement;

import java.util.Date;
import java.util.List;

public interface Replay extends NamedElement {

    List<Measurement> getMeasurements(Date startDate);
}
