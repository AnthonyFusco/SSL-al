package kernel.datasources.executables.replay;

import com.google.gson.*;
import kernel.datasources.Measurement;
import kernel.units.Duration;
import kernel.visitor.Visitor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class JsonReplay extends Replay {

    private String name;
    private String path;
    private Duration offset;
    private List<Integer> noiseRange;

    private String sensorNameToken;
    private String sensorRecordToken;
    private String sensorValueToken;
    private String sensorRelativeTimeToken;

    public JsonReplay() {
        this.name = "JSON Replay";
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public List<Measurement> generateNextMeasurement(double startDate) {
        JsonParser parser = new JsonParser();

        List<Measurement> measurementList = new ArrayList<>();
        try {
            JsonElement jsontree = parser.parse(new FileReader(path));

            JsonObject jo = jsontree.getAsJsonObject();
            JsonElement sensorname = jo.get(sensorNameToken);
            JsonArray record = jo.getAsJsonArray(sensorRecordToken);
            for (Object o : record) {
                JsonObject sensor = (JsonObject) o;

                JsonElement jsonvalue = sensor.get(sensorValueToken);
                Double value = jsonvalue.getAsDouble();
                if (noiseRange != null) {
                    Integer inf = noiseRange.get(0);
                    Integer sup = noiseRange.get(1);
                    double random = new Random().nextDouble();
                    double noiseValue = inf.doubleValue() + (random * (sup.doubleValue() - inf.doubleValue()));
                    value = value + noiseValue;
                }
                JsonElement jsontime = sensor.get(sensorRelativeTimeToken);
                long relativeTime = jsontime.getAsLong() + (long) startDate + (long) offset.getValue();
                Measurement m = new Measurement<>(sensorname.getAsString(), relativeTime, value);
                measurementList.add(m);
            }
            return measurementList;
        } catch (JsonIOException | FileNotFoundException | JsonSyntaxException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setOffset(Duration offset) {
        this.offset = offset;
    }

    public void setNoiseRange(List<Integer> noiseRange) {
        this.noiseRange = noiseRange;
    }

    public void setSensorNameToken(String sensorNameToken) {
        this.sensorNameToken = sensorNameToken;
    }

    public void setSensorRecordToken(String sensorRecordToken) {
        this.sensorRecordToken = sensorRecordToken;
    }

    public void setSensorValueToken(String sensorValueToken) {
        this.sensorValueToken = sensorValueToken;
    }

    public void setSensorRelativeTimeToken(String sensorRelativeTimeToken) {
        this.sensorRelativeTimeToken = sensorRelativeTimeToken;
    }

}
