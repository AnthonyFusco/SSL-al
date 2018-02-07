package dsl;

import builders.LawBuilder;
import builders.SensorsLotBuilder;
import groovy.lang.Binding;

import java.util.ArrayList;
import java.util.List;

public class SslModel {
    private List<SensorsLotBuilder> sensorsLotBuilders;
    private List<LawBuilder> lawsBuilders;

    private Binding binding;

    public SslModel(Binding binding) {
        this.binding = binding;
        sensorsLotBuilders = new ArrayList<>();
        lawsBuilders = new ArrayList<>();

    }

    /*public Law createLaw(String name, LawType strategy) {
        Law law = null;
        switch (strategy) {
            case RandomLaw:
                law = new RandomLaw();
                break;
            case MarkovLaw:
                law = new MarkovChainLaw();
                break;
            case File:
                law = new FileLaw();
                break;
            case FunctionLaw:
                law = new MathFunctionLaw();
                break;
        }
        law.setName(name);

        this.laws.add(law);
        this.binding.setVariable(name, law);
        return law;
    }*/


    public void addSensorsBuilder(SensorsLotBuilder builder) {
        sensorsLotBuilders.add(builder);
    }

    public void addLawBuilder(LawBuilder builder) {
        lawsBuilders.add(builder);
    }

    public List<SensorsLotBuilder> getSensorsLotBuilders() {
        return sensorsLotBuilders;
    }

    public List<LawBuilder> getLawsBuilders() {
        return lawsBuilders;
    }
}
