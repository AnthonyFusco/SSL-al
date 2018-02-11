package dsl;

import builders.LawBuilder;
import builders.ReplayBuilder;
import builders.SensorsLotBuilder;
import groovy.lang.Binding;
import kernel.structural.replay.Replay;

import java.util.ArrayList;
import java.util.List;

public class SslModel {
    private List<SensorsLotBuilder> sensorsLotBuilders;
    private List<LawBuilder> lawsBuilders;
    private List<ReplayBuilder> replayBuilders;
    private List<String> lawNames;

    private Binding binding;

    public SslModel(Binding binding) {
        this.binding = binding;
        sensorsLotBuilders = new ArrayList<>();
        lawsBuilders = new ArrayList<>();
        replayBuilders = new ArrayList<>();
        lawNames = new ArrayList<>();
    }

    public void addSensorsBuilder(SensorsLotBuilder builder) {
        sensorsLotBuilders.add(builder);
    }

    public void addLawBuilder(LawBuilder builder) {
        if (builder.getLawName() == null || builder.getLawName().isEmpty()) {
            throw new IllegalArgumentException("A law name cannot be null or empty");
        }
        if (lawNames.contains(builder.getLawName())) {
            throw new IllegalArgumentException("The name " + builder.getLawName() + " is already taken");
        }
        lawNames.add(builder.getLawName());
        lawsBuilders.add(builder);
    }

    public void addReplayBuilder(ReplayBuilder replayBuilder) {
        replayBuilders.add(replayBuilder);
    }

    public List<SensorsLotBuilder> getSensorsLotBuilders() {
        return sensorsLotBuilders;
    }

    public List<LawBuilder> getLawsBuilders() {
        return lawsBuilders;
    }

    public List<ReplayBuilder> getReplayBuilders() {
        return replayBuilders;
    }

    public List<String> getLawNames() {
        return lawNames;
    }
}
