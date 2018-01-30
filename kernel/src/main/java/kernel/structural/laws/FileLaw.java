package kernel.structural.laws;

import kernel.behavioral.DataSourceType;

public class FileLaw implements Law {
    private String name;
    private String path;
    private DataSourceType dataSourceType;

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
}
