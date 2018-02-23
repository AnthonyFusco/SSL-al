package builders;

import kernel.structural.composite.Composite;
import kernel.structural.laws.DataSource;
import kernel.structural.replay.JsonReplay;
import kernel.units.Duration;
import kernel.units.TimeUnit;

import java.util.List;

public class JsonReplayBuilder extends AbstractEntityBuilder<DataSource> {

    private String path;
    private Duration offset = new Duration(0, TimeUnit.Second);
    private List<Integer> noiseRange;

    public JsonReplayBuilder(int definitionLine) {
        super(definitionLine);
    }


    public JsonReplayBuilder path(String path){
        this.path = path;
        return this;
    }

    public JsonReplayBuilder offset(Duration offset){
        this.offset = offset;
        return this;
    }

    public JsonReplayBuilder noise(List<Integer> noiseRange){
        this.noiseRange = noiseRange;
        return this;
    }

    @Override
    public DataSource build() {
        JsonReplay jsonReplay = new JsonReplay();
        jsonReplay.setPath(path);
        jsonReplay.setOffset(offset);
        jsonReplay.setNoiseRange(noiseRange);
        jsonReplay.setExecutable(isExecutable());
        jsonReplay.setExecutableName(getExecutableName());
        return jsonReplay;
    }

    @Override
    public void validate() {

    }
}
