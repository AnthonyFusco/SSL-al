package dsl;

import exceptions.LawNotFoundException;
import groovy.lang.Binding;
import kernel.Application;
import kernel.structural.SensorsLot;
import kernel.structural.laws.Law;
import kernel.structural.laws.LawType;
import kernel.structural.laws.MarkovChainLaw;
import kernel.structural.laws.RandomLaw;
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
	
	public void createSensorsLot(String name, int sensorsNumber, String lawName, int simulationDuration)
            throws LawNotFoundException {

		Optional<Law> lawFound = findLawByName(lawName);
        if (!lawFound.isPresent()) {
            throw new LawNotFoundException();
        }
		SensorsLot sensorsLot = new SensorsLot(name, sensorsNumber, simulationDuration, lawFound.get());
        this.sensorsLots.add(sensorsLot);
        this.binding.setVariable(name, sensorsLot);
    }

    private Optional<Law> findLawByName(String lawName) {
	    if (Objects.isNull(lawName)) return Optional.empty();
        return laws.stream().filter(law -> law.getName().equals(lawName)).findFirst();
    }

    public Law createLaw(String name, LawType strategy) {
//	    if(Objects.isNull(name)) return;
//        if(Objects.isNull(strategy)) return;
		Law law = null;
		switch (strategy) {
			case Random: law = new RandomLaw(); break;
			case MarkovChain: law = new MarkovChainLaw(); break;
		}
		assert law != null;
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
