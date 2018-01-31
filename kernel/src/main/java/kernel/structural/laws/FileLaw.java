package kernel.structural.laws;

import kernel.Measurement;
import kernel.behavioral.DataSourceType;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.*;

public class FileLaw implements Law {
    private String name;
    private String path;
    private DataSourceType dataSourceType;
    private Map<String, Object> columnsDescriptions = new HashMap<>();

    @Override
    public Measurement generateNextMeasurement(int t) {
        if (dataSourceType.equals(DataSourceType.CSV)) { //CSVLaw maybe
            Integer tColumn = (Integer) columnsDescriptions.get("t");
            Integer sColumn = (Integer) columnsDescriptions.get("s");
            Integer vColumn = (Integer) columnsDescriptions.get("v");
            try {
                File csvData = new File(path);
                CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT);

                List<CSVRecord> records = parser.getRecords();
                records.remove(0); //remove header

                if (records.size() <= t) {
                    return null;
                }

                CSVRecord currentRecord = records.get(t);

                String timestamp = String.valueOf(Instant.now().getEpochSecond()) + "000000000"; //for debug

                return new Measurement(currentRecord.get(sColumn).trim(),
                        currentRecord.get(tColumn).trim() + "000000000",
                        currentRecord.get(vColumn).trim());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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

    public void setDataSourceType(DataSourceType dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setColumnsDescriptions(Map<String, Object> columnsDescriptions) {
        this.columnsDescriptions = columnsDescriptions;
    }
}
