package com.unice.dsl.engine

final class SslBinding extends Binding {
    private Script script

    private SslModel model

    SslBinding() {
        super()
    }

    SslBinding(Map variables) {
        super(variables)
    }

    SslBinding(Script script) {
        super()
        this.script = script
    }

    void setScript(Script script) {
        this.script = script
    }

    Object getVariable(String name) {
        return super.getVariable(name)
    }

    void setVariable(String name, Object value) {
        super.setVariable(name, value)
    }

    SslModel getModel() {
        return this.model
    }

    void setModel(SslModel model) {
        this.model = model
    }
}