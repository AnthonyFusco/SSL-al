package kernel.visitor;

public interface Visitable {
    void accept(Visitor visitor);
}
