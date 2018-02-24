package kernel.visitor;

import kernel.Application;
import kernel.datasources.executables.replay.Replay;
import kernel.datasources.executables.simulations.Simulation;

public interface Visitor {
    void visit(Application application);

    void visit(Simulation simulation);

    void visit(Replay replay);
}
