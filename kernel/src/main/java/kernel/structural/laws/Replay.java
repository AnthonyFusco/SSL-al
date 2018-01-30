package kernel.structural.laws;

import kernel.Measurement;
import kernel.structural.SensorsLot;
import kernel.visitor.Visitable;
import kernel.visitor.Visitor;

import java.util.List;

public class Replay extends AbstractLaw implements Visitable {

    @Override
    public List<Measurement> play(SensorsLot lot) {
        return null;
    }

    @Override
    public Law applyLaw(Law law) {
        return null;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
