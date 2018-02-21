package kernel;

import kernel.structural.laws.DataSource;
import kernel.structural.replay.Replay;
import kernel.visitor.ExecutableSource;
import kernel.visitor.Visitable;
import kernel.visitor.Visitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application implements NamedElement, Visitable {
    private String name;
    private Date endDate;
    private Date startDate;
    private List<DataSource> dataSources = new ArrayList<>();
    private List<Replay> replays = new ArrayList<>();
    private List<ExecutableSource> executableSources = new ArrayList<>();

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

    public List<Replay> getReplays() {
        return replays;
    }

    public void addExecutableSource(ExecutableSource dataSource) {
        this.executableSources.add(dataSource);
    }

    public List<ExecutableSource> getExecutableSources() {
        return executableSources;
    }
}