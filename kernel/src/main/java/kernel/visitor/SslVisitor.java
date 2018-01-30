package kernel.visitor;

import kernel.Application;
import kernel.structural.SensorsLot;

public class SslVisitor implements Visitor {

    @Override
    public void visit(Application application) {

    }


    @Override
    public void visit(SensorsLot lot) {
        lot.getLaw().play(lot);
    }

}
