package builders;

import dsl.SslModel;
import kernel.behavioral.DataSourceType;
import kernel.structural.laws.FileLaw;

import java.util.Map;

public class ReplayBuilder implements EntityBuilder<FileLaw>{
    private String replayName;
    private String path;
    private DataSourceType format;
    private Map<String, Object> columnsDescriptions;
    public ReplayBuilder(String replayName) {
        this.replayName = replayName;
    }

    public ReplayBuilder fromPath(String path) {
        this.path = path;
        return this;
    }

    public ReplayBuilder format(String format) {
        this.format = DataSourceType.valueOf(format);
        return this;
    }

    public ReplayBuilder withColumns(Map<String, Object> columnsDescriptions) {
        this.columnsDescriptions = columnsDescriptions;
        return this;
    }

    @Override
    public FileLaw build() {
        FileLaw law = new FileLaw();
        law.setName(replayName);
        law.setDataSourceType(format);
        law.setPath(path);
        law.setColumnsDescriptions(columnsDescriptions);
        return law;
    }

    @Override
    public void validate(SslModel model) {

    }
}
