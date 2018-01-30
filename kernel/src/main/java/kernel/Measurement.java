package kernel;

public class Measurement {
    private String sensorName;
    private int timeStamp;
    private Object value;

    public Measurement(String sensorName, int timeStamp, Object value) {
        this.sensorName = sensorName;
        this.timeStamp = timeStamp;
        this.value = value;
    }

    public int getTimeStamp() {
        return timeStamp;
    }

    public Object getValue() {
        return value;
    }

    public String getSensorName() {
        return sensorName;
    }

    @Override
    public String toString() {
        return "(" + timeStamp + ", " + sensorName + ", " + value + ")";
    }
}
