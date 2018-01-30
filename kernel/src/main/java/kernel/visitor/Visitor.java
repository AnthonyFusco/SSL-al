package kernel.visitor;

import kernel.Application;
import kernel.structural.laws.Modelling;
import kernel.structural.laws.Replay;

public interface Visitor {
    void visit(Application application);
    void visit(Modelling law);
    void visit(Replay law);
}
