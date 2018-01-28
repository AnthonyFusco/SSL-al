package structural;

import kernel.NamedElement;

public class Sensor implements NamedElement {
    private String name;


    @Override
    public String getName() {
        return name;
    }
}
