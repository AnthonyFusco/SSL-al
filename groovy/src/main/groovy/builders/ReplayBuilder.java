package builders;

import dsl.SslModel;
import kernel.structural.replay.CSVReplay;
import kernel.structural.replay.Replay;

import java.util.Map;

public class ReplayBuilder implements EntityBuilder<Replay>{
    private String replayName;
    private String path;
//    private DataSourceType format;
    private Map<String, Object> columnsDescriptions;
    private long offset = 0;

    public ReplayBuilder(String replayName) {
        this.replayName = replayName;
    }

    public ReplayBuilder fromPath(String path) {
        this.path = path;
        return this;
    }

    /*public ReplayBuilder format(String format) {
        this.format = DataSourceType.valueOf(format);
        return this;
    }*/

    public ReplayBuilder withColumns(Map<String, Object> columnsDescriptions) {
        this.columnsDescriptions = columnsDescriptions;
        return this;
    }

    public ReplayBuilder withOffset(long offset) {
        this.offset = offset;
        return this;
    }

    public ReplayBuilder withNoise(String noise) {
        return this; //todo todo :)
    }

    @Override
    public Replay build() {
        CSVReplay replay = new CSVReplay(); //only csv for now
        replay.setName(replayName);
        replay.setPath(path);
        replay.setColumnsDescriptions(columnsDescriptions);
        replay.setOffset(offset);
        return replay;
    }

    @Override
    public void validate(SslModel model) {

    }
}
