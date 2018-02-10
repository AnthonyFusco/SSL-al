package kernel.structural.replay;

import kernel.Measurement;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CSVReplay implements Replay {
    private String name;
    private String path;
    private Map<String, Object> columnsDescriptions = new HashMap<>();
    private long offset;

    @Override
    public List<Measurement> getMeasurements() {
        Integer tColumn = (Integer) columnsDescriptions.get("t");
        Integer sColumn = (Integer) columnsDescriptions.get("s");
        Integer vColumn = (Integer) columnsDescriptions.get("v");
        try {
            File csvData = new File(path);
            CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT);

            List<CSVRecord> records = parser.getRecords();
            if (records.size() > 1) {
                records.remove(0); //remove header
            }

            List<Measurement> measurementList = new ArrayList<>();
            for (CSVRecord record : records) {
                Measurement measurement = new Measurement<>(
                        record.get(sColumn).trim(),
                        Long.parseLong(record.get(tColumn).trim()) + offset,
                        record.get(vColumn).trim()
                );

                measurementList.add(measurement);
            }

            return measurementList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setColumnsDescriptions(Map<String, Object> columnsDescriptions) {
        this.columnsDescriptions = columnsDescriptions;
    }

    public void setOffset(long offset) {
        this.offset = offset;
    }
}
