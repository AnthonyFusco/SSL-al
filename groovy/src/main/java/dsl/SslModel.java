package dsl;

import exceptions.LawNotFoundException;
import groovy.lang.Binding;
import kernel.Application;
import kernel.structural.SensorsLot;
import kernel.structural.laws.*;
import kernel.structural.laws.markov.MarkovChainLaw;
import kernel.visitor.SslVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class SslModel {
    private List<SensorsLot> sensorsLots;
    private List<Law> laws;
	
	private Binding binding;
	
	public SslModel(Binding binding) {
		this.binding = binding;
		sensorsLots = new ArrayList<>();
		laws = new ArrayList<>();
	}
	
	public SensorsLot createSensorsLot(String name, int sensorsNumber, String lawName)
            throws LawNotFoundException {

		Optional<Law> lawFound = findLawByName(lawName);
        if (!lawFound.isPresent()) {
            throw new LawNotFoundException();
        }
		SensorsLot sensorsLot = new SensorsLot(name, sensorsNumber, lawFound.get());
        this.sensorsLots.add(sensorsLot);
        this.binding.setVariable(name, sensorsLot);
        return sensorsLot;
    }

    private Optional<Law> findLawByName(String lawName) {
	    if (Objects.isNull(lawName)) return Optional.empty();
        return laws.stream().filter(law -> law.getName().equals(lawName)).findFirst();
    }

    public Law createLaw(String name, LawType strategy) {
		Law law = null;
		switch (strategy) {
			case Random: law = new RandomLaw(); break;
			case MarkovChain: law = new MarkovChainLaw(); break;
            case File: law = new FileLaw(); break;
            case MathFunction: law = new MathFunctionLaw(); break;
		}
		law.setName(name);

		this.laws.add(law);
		this.binding.setVariable(name, law);
		return law;
	}

	public void runSimulation(String name) {
		if (Objects.isNull(name)) return;
        Application app = new Application();
		app.setDeclaredSensorsLots(sensorsLots);

		SslVisitor visitor = new SslVisitor();
		visitor.visit(app);
    }
}
