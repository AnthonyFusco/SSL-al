package units;

import groovy.transform.TupleConstructor;

@TupleConstructor
public class Frequency {
    private Duration duration;
    private int occurrences;

    public Frequency(int occurrences, Duration duration) {
        this.duration = duration;
        this.occurrences = occurrences;
    }


    public double getValue() {
        return (double)occurrences / duration.getValue();
    }
}