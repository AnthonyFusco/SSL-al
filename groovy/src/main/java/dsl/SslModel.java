package dsl;

import java.util.*;

import groovy.lang.Binding;
import kernel.Application;
import kernel.generator.CodeGenerator;
import kernel.generator.Visitor;
import kernel.structural.SensorsLot;
import kernel.structural.laws.Law;

public class SslModel {
    private List<SensorsLot> sensorsLots;
    private List<Law> laws;
	
	private Binding binding;
	
	public SslModel(Binding binding) {
		this.binding = binding;
		sensorsLots = new ArrayList<>();
		laws = new ArrayList<>();
	}
	
	public void createSensorsLot(String name, int sensorsNumber, String lawName) {
		SensorsLot sensorsLot = new SensorsLot();
		sensorsLot.setName(name);

		this.sensorsLots.add(sensorsLot);
		this.binding.setVariable(name, sensorsLot);
	}

	public void createLaw(String name, String strategy) {
		SensorsLot sensorsLot = new SensorsLot();
		sensorsLot.setName(name);

		this.sensorsLots.add(sensorsLot);
		this.binding.setVariable(name, sensorsLot);
	}

	public Object generateCode(String appName) {
        Application app = new Application();
		app.setName(appName);
		app.setSensorsLots(sensorsLots);
		Visitor codeGenerator = new CodeGenerator();
		app.accept(codeGenerator);
		
		return codeGenerator.getResult();
	}
}
