package kernel.visitor;

import kernel.Application;
import kernel.structural.SensorsLot;
import kernel.structural.composite.Composite;
import kernel.structural.replay.Replay;

public interface Visitor {
    void visit(Application application);

    /*void visit(Composite composite);

    void visit(SensorsLot sensorsLot);*/

    void visit(ExecutableSource executableSource);

    void visit(Replay replay);
}
