package kernel.structural.replay;

import com.google.gson.*;
import kernel.Measurement;
import kernel.units.Duration;
import kernel.visitor.Visitor;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonReplay implements Replay {

    private boolean isExecutable;
    private String name;
    private String path;
    private Duration offset;
    private List<Integer> noiseRange;

    private String sensorNameToken = "bn";
    private String sensorRecordToken = "e";
    private String sensorValueToken = "v";
    private String sensorRelativeTimeToken = "t";
    private String executableName = "";

    public JsonReplay(){
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
            for (Object o : record)
            {
                JsonObject sensor = (JsonObject) o;

                JsonElement jsonvalue = sensor.get(sensorValueToken);
                Double value = jsonvalue.getAsDouble();
                JsonElement jsontime = sensor.get(sensorRelativeTimeToken);
                long relativeTime = jsontime.getAsLong() + (long)startDate + (long)offset.getValue();
                Measurement m = new Measurement(sensorname.getAsString(),relativeTime,value);
//                System.out.println(m.toString());
                measurementList.add(m);
            }
            return measurementList;
        }
        catch (JsonIOException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
    @Override
    public boolean isExecutable() {
        return isExecutable;
    }

    public void setExecutable(boolean executable) {
        isExecutable = executable;
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

    public void setExecutableName(String executableName) {
        this.executableName = executableName;
    }

    public String getExecutableName() {
        return executableName;
    }
}
