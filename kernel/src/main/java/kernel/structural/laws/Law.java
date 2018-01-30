package kernel.structural.laws;

import kernel.NamedElement;

public interface Law extends NamedElement {
    Object generateNextValue(int t);
}
