package dsl;

import groovy.lang.Binding;
import groovy.lang.Script;

import java.util.Map;

public class SslBinding extends Binding {
    private Script script;

    private SslModel model;

    public SslBinding() {
        super();
    }


    public SslBinding(Map variables) {
        super(variables);
    }

    public SslBinding(Script script) {
        super();
        this.script = script;
    }

    public void setScript(Script script) {
        this.script = script;
    }

    public Object getVariable(String name) {
        return super.getVariable(name);
    }

    public void setVariable(String name, Object value) {
        super.setVariable(name, value);
    }

    public SslModel getModel() {
        return this.model;
    }

    public void setModel(SslModel model) {
        this.model = model;
    }
}
