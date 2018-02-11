package kernel.units;

public enum TimeUnit {
    Second("s", 1.0), Minute("min", 60.0), Hour("h", 3600.0), Day("d", 86400.0);

    private String abbreviation;
    private double secondsNumber;

    TimeUnit(String abbreviation, double secondsNumber) {
        this.abbreviation = abbreviation;
        this.secondsNumber = secondsNumber;
    }

    @Override
    public String toString() {
        return abbreviation;
    }

    public double getMillisecondsNumber() {
        return secondsNumber * 1000;
    }
}
