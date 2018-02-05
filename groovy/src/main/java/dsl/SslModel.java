package dsl;

import builders.SensorsLotBuilder;
import groovy.lang.Binding;
import kernel.structural.laws.*;
import kernel.structural.laws.markov.MarkovChainLaw;

import java.util.ArrayList;
import java.util.List;

public class SslModel {
    private List<Law> laws;
    private List<SensorsLotBuilder> sensorsLotBuilders;

    private Binding binding;

    public SslModel(Binding binding) {
        this.binding = binding;
        laws = new ArrayList<>();
        sensorsLotBuilders = new ArrayList<>();
    }

    public Law createLaw(String name, LawType strategy) {
        Law law = null;
        switch (strategy) {
            case Random:
                law = new RandomLaw();
                break;
            case MarkovChain:
                law = new MarkovChainLaw();
                break;
            case File:
                law = new FileLaw();
                break;
            case MathFunction:
                law = new MathFunctionLaw();
                break;
        }
        law.setName(name);

        this.laws.add(law);
        this.binding.setVariable(name, law);
        return law;
    }


    public void addSensorsBuilder(SensorsLotBuilder builder) {
        sensorsLotBuilders.add(builder);
    }

    public List<SensorsLotBuilder> getSensorsLotBuilders() {
        return sensorsLotBuilders;
    }

    public List<Law> getLaws() {
        return laws;
    }
}
