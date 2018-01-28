package kernel.structural;

import kernel.NamedElement;

public class Sensor implements NamedElement {
    private String name;


    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {

    }
}
