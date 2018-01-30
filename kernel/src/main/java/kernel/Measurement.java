package kernel;

public class Measurement {
    private String sensorName;
    private String timeStamp;
    private Object value;

    public Measurement(String sensorName, String timeStamp, Object value) {
        this.sensorName = sensorName;
        this.timeStamp = timeStamp;
        this.value = value;
    }

    public String getTimeStamp() {
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
