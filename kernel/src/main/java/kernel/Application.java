package kernel;

import kernel.structural.SensorsLot;
import kernel.structural.laws.Law;
import kernel.visitor.Visitable;
import kernel.visitor.Visitor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Application implements NamedElement, Visitable {
    private List<SensorsLot> sensorsLots = new ArrayList<>();
    private List<Law> laws = new ArrayList<>();
    private String name;
    private Date endDate;
    private Date startDate;

    public List<Law> getLaws() {
        return laws;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setDeclaredSensorsLots(List<SensorsLot> sensorsLots) {
        this.sensorsLots = sensorsLots;
    }

    public List<SensorsLot> getSensorsLots() {
        return sensorsLots;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public Date getStartDate() {
        return startDate;
    }
}