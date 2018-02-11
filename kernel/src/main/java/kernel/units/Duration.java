package kernel.units;

import groovy.transform.TupleConstructor;

@TupleConstructor
public class Duration implements Comparable<Duration> {
    private double amount;
    private TimeUnit unit;

    public Duration(double amount, TimeUnit unit) {
        this.amount = amount;
        this.unit = unit;
    }

    public double getValue() {
        return amount * unit.getMillisecondsNumber();
    }

    @Override
    public int compareTo(Duration duration) {
        return Double.compare(amount * unit.getMillisecondsNumber(), duration.amount * duration.unit.getMillisecondsNumber());
    }
}
