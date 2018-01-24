package kernel;

import kernel.structural.Sensor;

public class Measurement {
    private Sensor sensor;
    private int timeStamp;
    private Object value;

    public Measurement(Sensor sensor, int timeStamp, Object value) {
        this.sensor = sensor;
        this.timeStamp = timeStamp;
        this.value = value;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public Object getValue() {
        return value;
    }

    public Sensor getSensor() {
        return sensor;
    }
}
