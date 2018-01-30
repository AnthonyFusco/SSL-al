package dsl;

import exceptions.LawNotFoundException;
import groovy.lang.Binding;
import kernel.structural.SensorsLot;
import kernel.structural.laws.Law;
import kernel.structural.laws.Replay;

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
	
	public void createSensorsLot(String name, int sensorsNumber, String lawName) throws LawNotFoundException {
		SensorsLot sensorsLot = new SensorsLot();
		sensorsLot.setName(name);

		Optional<Law> lawFound = findLawByName(lawName);
        if (!lawFound.isPresent()) {
            throw new LawNotFoundException();
        }
        sensorsLot.setLaw(lawFound.get());
        sensorsLot.setSensorsNumber(sensorsNumber);
        this.sensorsLots.add(sensorsLot);
        this.binding.setVariable(name, sensorsLot);
    }

    private Optional<Law> findLawByName(String lawName) {
	    if (Objects.isNull(lawName)) return Optional.empty();
        return laws.stream().filter(law -> law.getName().equals(lawName)).findFirst();
    }

    public void createLaw(String name, String strategy) {
	    if(Objects.isNull(name)) return;
		Law law = new Replay();
		law.setName(name);

		this.laws.add(law);
		this.binding.setVariable(name, law);
	}

	/*public String generateCode(String appName) {
        Application app = new Application();
		app.setName(appName);
		app.setDeclaredLaws(laws);
		app.setDeclaredSensorsLots(sensorsLots);
		
		return codeGenerator.getResult().toString();
	}*/
}
