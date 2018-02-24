package kernel;

import kernel.datasources.NamedElement;
import kernel.datasources.laws.DataSource;
import kernel.visitor.Visitable;
import kernel.visitor.Visitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application implements NamedElement, Visitable {
    private String name;
    private Date endDate;
    private Date startDate;
    //    private List<Replay> replays = new ArrayList<>();
//    private List<Simulation> simulations = new ArrayList<>();
    private List<DataSource> dataSources = new ArrayList<>();

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

    /*public void addReplay(Replay replay) {
        this.replays.add(replay);
    }

    public List<Replay> getReplays() {
        return replays;
    }

    public void addSimulation(Simulation simulation) {
        this.simulations.add(simulation);
    }

    public List<Simulation> getExecutableSources() {
        return simulations;
    }*/

    public void addDataSource(DataSource dataSource) {
        this.dataSources.add(dataSource);
    }

    public List<DataSource> getDataSources() {
        return dataSources;
    }
}