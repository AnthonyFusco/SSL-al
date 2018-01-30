package kernel.visitor;

import kernel.Application;
import kernel.structural.SensorsLot;

public interface Visitor {
    void visit(Application application);
    void visit(SensorsLot lot);
}
