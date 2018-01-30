package kernel.visitor;

import kernel.Application;
import kernel.structural.Sensor;
import kernel.structural.SensorsLot;

public class SslVisitor implements Visitor {

    @Override
    public void visit(Application application) {
        for (SensorsLot sensorsLot : application.getSensorsLots()) {
            visitSensorsLot(sensorsLot);
        }
    }

    private void visitSensorsLot(SensorsLot lot) {
        for (int t = 0; t < lot.getSimulationDuration(); t++) {
            for (Sensor sensor : lot.getSensors()) {
                System.out.println(sensor.generateNextMeasurement(t));
            }
        }
    }

}
