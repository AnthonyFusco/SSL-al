package kernel.units;

import groovy.transform.TupleConstructor;

@TupleConstructor
public class Frequency {
    private Duration duration;
    private int occurrences;

    public Frequency(int occurrences, Duration duration) {
        this.duration = duration;
        this.occurrences = occurrences;
    }

    public Duration getDuration() {
        return duration;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public double getValue() {
        return (double) occurrences / duration.getValue();
    }

    @Override
    public String toString() {
        return occurrences + "/" + duration;
    }
}