package builders;

import dsl.SslModel;
import kernel.structural.composite.Composite;
import kernel.structural.replay.CSVReplay;
import kernel.structural.replay.Replay;
import kernel.units.Duration;
import kernel.units.TimeUnit;
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

public class ReplayBuilder extends AbstractEntityBuilder<Replay> {
    private String name;
    private String path;
    //private DataSourceType format;
    private Map<String, Object> columnsDescriptions;
    private Duration offset = new Duration(0, TimeUnit.Second);
    private List<Integer> noise;

    public ReplayBuilder(String name) {
        this.name = name;
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

    public ReplayBuilder withOffset(Duration offset) {
        this.offset = offset;
        return this;
    }

    public ReplayBuilder withNoise(List<Integer> noise) {
        this.noise = noise;
        return this; //todo todo :)
    }

    @Override
    public Replay build() {
        CSVReplay replay = new CSVReplay(); //only csv for now
        replay.setName(name);
        replay.setPath(path);
        replay.setColumnsDescriptions(columnsDescriptions);
        replay.setOffset((long) offset.getValue());
        replay.setNoise(this.noise);
        return replay;
    }

    @Override
    public void validate(SslModel model) {
        if (name == null || name.isEmpty()) {
            addError(new IllegalArgumentException("The name of a Replay must not be empty"));
        }

        if (columnsDescriptions.size() < 3) {
            System.out.println("\u001B[33mWARNING : Columns descriptions of " + name + " is empty or not complete\n" +
                    "Using default description : withColumns([t: 0, s: 1, v: 2])");
            Map<String, Object> defaultDescription = new HashMap<>();
            defaultDescription.put("t", 0);
            defaultDescription.put("v", 1);
            defaultDescription.put("s", 2);
            columnsDescriptions = defaultDescription;
        }

        if (path == null || path.isEmpty()) {
            addError(new IllegalArgumentException("The path of " + name + " must not be empty"));
        }

        File file = new File(path);
        if (!file.exists() || !file.canRead() || !file.isFile()) {
            addError(new IllegalArgumentException("The path of " + name + " must be a valid file"));
        }

        try {
            CSVParser parser = CSVParser.parse(file, Charset.defaultCharset(), CSVFormat.DEFAULT);
            List<CSVRecord> records = parser.getRecords();
            if (records.isEmpty()) {
                addError(new IllegalArgumentException("The csv file of " + name + " must not be empty"));
            }

            int isAllConsistent = (int) records.stream()
                    .mapToInt(CSVRecord::size)
                    .distinct()
                    .count();
            if (isAllConsistent > 1) {
                addError(new IllegalArgumentException("The csv file of " + name +
                        " is inconsistent (all lines must have the same number of columns)"));
            }

            Integer tColumn = (Integer) columnsDescriptions.get("t");
            Integer sColumn = (Integer) columnsDescriptions.get("s");
            Integer vColumn = (Integer) columnsDescriptions.get("v");
            if (records.size() > 1) {
                records.remove(0); //remove header
            }
            //parsing the whole file might be long...
            String s = records.get(0).get(sColumn);
            if (s == null) {
                addError(new IllegalArgumentException("The s column of CSV " + name + " is not correct"));
            }
            Object v = records.get(0).get(vColumn);
            if (v == null) {
                addError(new IllegalArgumentException("The v column of CSV " + name + " is not correct"));
            }
            Object t = records.get(0).get(tColumn);
            if (t == null) {
                addError(new IllegalArgumentException("The t column of CSV " + name + " is not correct"));
            }
            try {
                long l = Long.parseLong(t.toString());
            } catch (NumberFormatException e) {
                addError(new IllegalArgumentException("The t column of CSV " + name + " is not a time"));
            }
        } catch (IOException e) {
            System.out.println("Error while parsing the CSV file for replay " + name);
            e.printStackTrace();
        }

        if(noise != null){
            if (noise.size() != 2){
                addError(new IllegalArgumentException("You must specify a valid noise interval"));
            }
        }
    }
}
