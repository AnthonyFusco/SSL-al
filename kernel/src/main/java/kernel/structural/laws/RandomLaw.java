package kernel.structural.laws;

import kernel.Measurement;

import java.time.Instant;
import java.util.Random;

public class RandomLaw implements Law {
    private String name;

    @Override
    public Measurement generateNextMeasurement(int t) {
        String timestamp = String.valueOf(Instant.now().getEpochSecond()) + "000000000";
        return new Measurement(name,timestamp, new Random().nextInt() % 10);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }
}
