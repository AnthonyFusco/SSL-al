package kernel;

public class Measurement<T> {
    private String sensorName;
    private Long timeStamp;
    private T value;
    private Class clazz;

    public Measurement(String sensorName, long timeStamp, T value) {
        this.sensorName = sensorName;
        this.timeStamp = timeStamp;
        this.value = value;
        this.clazz = value.getClass();
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

    public Class getClazz() {
        return clazz;
    }
}
