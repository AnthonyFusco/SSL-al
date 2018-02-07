package kernel;

public class Measurement<T> {
    private String sensorName;
    private Long timeStamp;
    private T value;

    public Measurement(String sensorName, long timeStamp, T value) {
        this.sensorName = sensorName;
        this.timeStamp = timeStamp;
        this.value = value;
    }

    public Long getTimeStamp() {
        return timeStamp;
    }

    public T getValue() {
        return value;
    }

    public String getSensorName() {
        return sensorName;
    }

    @Override
    public String toString() {
        return "(" + timeStamp + ", " + sensorName + ", " + value + ")";
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
}
