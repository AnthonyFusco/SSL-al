package kernel.structural.laws;

import java.util.Random;

public class RandomLaw implements Law {
    private String name;

    @Override
    public Object generateNextValue(int t) {
        return new Random().nextInt() % 10;
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
