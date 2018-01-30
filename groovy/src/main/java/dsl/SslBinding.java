package dsl;

import java.util.Map;

import groovy.lang.Binding;
import groovy.lang.Script;

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

	public void setModel(SslModel model) {
		this.model = model;
	}

	public Object getVariable(String name) {
		// Easter egg (to show you this trick: seb is now a keyword!)
		if ("runSimulation".equals(name)) {
			// could do something else like: ((App) this.getVariable("app")).action();
			getModel().runSimulation();
			return script;
		}
		return super.getVariable(name);
	}
	
	public void setVariable(String name, Object value) {
		super.setVariable(name, value);
	}
	
	public SslModel getModel() {
		return this.model;
	}
}
