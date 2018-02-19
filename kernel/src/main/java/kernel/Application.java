package kernel;

import kernel.structural.SensorsLot;
import kernel.structural.composite.Composite;
import kernel.structural.laws.DataSource;
import kernel.structural.replay.Replay;
import kernel.visitor.Visitable;
import kernel.visitor.Visitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application implements NamedElement, Visitable {
    private List<SensorsLot> sensorsLots = new ArrayList<>();
    private List<DataSource> dataSources = new ArrayList<>();
    private List<Replay> replays = new ArrayList<>();
    private List<Composite> composites = new ArrayList<>();
    private String name;
    private Date endDate;
    private Date startDate;
    private List<String> toPlay = new ArrayList<>();

    public List<DataSource> getDataSources() {
        return dataSources;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public List<SensorsLot> getSensorsLots() {
        return sensorsLots;
    }

    public List<Replay> getReplays() {
        return replays;
    }

    public List<Composite> getComposites() {
        return composites;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void addReplay(Replay replay) {
        this.replays.add(replay);
    }

    public void addSensorLot(SensorsLot sensorsLot) {
        this.sensorsLots.add(sensorsLot);
    }

    public void addComposite(Composite composite) {
        this.composites.add(composite);
    }

    public void setToPlay(List<String> toPlay) {
        this.toPlay = toPlay;
    }

    public List<String> getToPlay() {
        return toPlay;
    }
}