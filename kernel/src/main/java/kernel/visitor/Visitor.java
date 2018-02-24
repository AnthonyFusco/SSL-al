package kernel.visitor;

import kernel.Application;
import kernel.structural.Simulation;
import kernel.structural.replay.Replay;

public interface Visitor {
    void visit(Application application);

    /*void visit(Composite composite);

    void visit(SensorsLot sensorsLot);*/

    void visit(Simulation executableSource);

    void visit(Replay replay);
}
