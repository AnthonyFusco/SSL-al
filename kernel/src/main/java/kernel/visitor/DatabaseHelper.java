package kernel.visitor;

import kernel.Measurement;

import java.util.List;

public interface DatabaseHelper {
    void sendToDatabase(List<Measurement> measurements, String sensorLotName, String lawName);
}
