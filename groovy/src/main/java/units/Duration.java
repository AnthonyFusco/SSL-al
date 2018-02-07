package units;

import groovy.transform.TupleConstructor;

@TupleConstructor
public class Duration implements Comparable<Duration> {
    private double amount;
    private TimeUnit unit;

    public Duration(double amount, TimeUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    @Override
    public int compareTo(Duration duration) {
        return Double.compare(amount * unit.getSecondsNumber(), duration.amount * duration.unit.getSecondsNumber());
    }
}
