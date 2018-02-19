package kernel.structural.replay;

import kernel.Measurement;
import kernel.visitor.Visitor;

import java.util.Date;
import java.util.List;

public class JsonReplay implements Replay {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public List<Measurement> generateNextMeasurement(double t) {
        return null;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
