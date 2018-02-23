package kernel.structural.replay;

import kernel.Measurement;
import kernel.visitor.Visitor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.util.*;

public class CSVReplay implements Replay {
    private String name;
    private String path;
    private Map<String, Object> columnsDescriptions = new HashMap<>();
    private long offset;
    private List<BigDecimal> noise;
    private boolean isExecutable;
    private String executableName = "";

    public CSVReplay() {
        this.name = "CSV Replay";
    }

    @Override
    public List<Measurement> generateNextMeasurement(double startDate) {
        Integer tColumn = (Integer) columnsDescriptions.get("t");
        Integer sColumn = (Integer) columnsDescriptions.get("s");
        Integer vColumn = (Integer) columnsDescriptions.get("v");

        double noiseValue;

        try {
            File csvData = new File(path);
            CSVParser parser = CSVParser.parse(csvData, Charset.defaultCharset(), CSVFormat.DEFAULT);

            List<CSVRecord> records = parser.getRecords();
            if (records.size() > 1) {
                records.remove(0); //remove header
            }

            List<Measurement> measurementList = new ArrayList<>();
            for (CSVRecord record : records) {
                String s = record.get(sColumn).trim();
                Long t = Long.parseLong(record.get(tColumn).trim()) + (long) startDate + offset;
                Object v = record.get(vColumn).trim();
                if (noise != null) {
                    BigDecimal inf = noise.get(0);
                    BigDecimal sup = noise.get(1);
                    /*noiseValue = new Random()
                            .nextInt(sup + 1 - inf) + inf;*/
                    double random = new Random().nextDouble();
                    noiseValue = inf.doubleValue() + (random * (sup.doubleValue() - inf.doubleValue()));
                    v = Double.parseDouble((String) v) + noiseValue;
                }
                measurementList.add(new Measurement<>(s, t, v));
            }

            return measurementList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public boolean isExecutable() {
        return isExecutable;
    }

    public void setExecutable(boolean executable) {
        isExecutable = executable;
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

    public void setNoise(List<BigDecimal> noise) {
        this.noise = noise;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void setExecutableName(String executableName) {
        this.executableName = executableName;
    }

    public String getExecutableName() {
        return executableName;
    }
}
