package kernel.visitor;

import kernel.datasources.Measurement;

import java.util.List;

public interface DatabaseHelper {
    void sendToDatabase(List<Measurement> measurements, String sensorLotName, String lawName);
}
