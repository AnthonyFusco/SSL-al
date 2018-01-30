package kernel.structural.laws;

import kernel.behavioral.DataSourceType;

import java.util.HashMap;
import java.util.Map;

public class FileLaw implements Law {
    private String name;
    private String path;
    private DataSourceType dataSourceType;
    private Map<String, Object> columnsDescriptions = new HashMap<>();

    @Override
    public Object generateNextValue(int t) {
        return null;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public DataSourceType getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Object> getColumnsDescriptions() {
        return columnsDescriptions;
    }

    public void setColumnsDescriptions(Map<String, Object> columnsDescriptions) {
        this.columnsDescriptions = columnsDescriptions;
    }
}
