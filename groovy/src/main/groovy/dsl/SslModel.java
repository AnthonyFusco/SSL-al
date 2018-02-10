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

    private Binding binding;

    public SslModel(Binding binding) {
        this.binding = binding;
        sensorsLotBuilders = new ArrayList<>();
        lawsBuilders = new ArrayList<>();
        replayBuilders = new ArrayList<>();
    }

    public void addSensorsBuilder(SensorsLotBuilder builder) {
        sensorsLotBuilders.add(builder);
    }

    public void addLawBuilder(LawBuilder builder) {
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
}
