package builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kernel.Measurement;
import kernel.structural.laws.DataSource;
import kernel.structural.replay.JsonReplay;
import kernel.units.Duration;
import kernel.units.TimeUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonReplayBuilder extends AbstractEntityBuilder<DataSource> {

    private String path;
    private Duration offset = new Duration(0, TimeUnit.Second);
    private List<Integer> noiseRange;
    private String sensorNameToken = "bn";
    private String sensorRecordToken = "e";
    private String sensorValueToken = "v";
    private String sensorRelativeTimeToken = "t";

    public JsonReplayBuilder(int definitionLine) {
        super(definitionLine);
    }

    public JsonReplayBuilder sensorRecordToken(String sensorRecordToken){
        this.sensorRecordToken = "e";
        return this;
    }

    public JsonReplayBuilder sensorValueToken(String sensorValueToken){
        this.sensorValueToken = "v";
        return this;
    }

    public JsonReplayBuilder sensorRelativeTimeToken(String sensorRelativeTimeToken){
        this.sensorRelativeTimeToken = "t";
        return this;
    }

    public JsonReplayBuilder sensorNameToken(String sensorNameToken){
        this.sensorNameToken = "bn";
        return this;
    }

    public JsonReplayBuilder path(String path) {
        this.path = path;
        return this;
    }

    public JsonReplayBuilder offset(Duration offset) {
        this.offset = offset;
        return this;
    }

    public JsonReplayBuilder noise(List<Integer> noiseRange) {
        this.noiseRange = noiseRange;
        return this;
    }

    @Override
    public DataSource build() {
        JsonReplay jsonReplay = new JsonReplay();
        jsonReplay.setPath(path);
        jsonReplay.setOffset(offset);
        jsonReplay.setNoiseRange(noiseRange);
        jsonReplay.setExecutable(isExecutable());
        jsonReplay.setExecutableName(getExecutableName());
        jsonReplay.setSensorNameToken(sensorNameToken);
        jsonReplay.setSensorRecordToken(sensorRecordToken);
        jsonReplay.setSensorRelativeTimeToken(sensorRelativeTimeToken);
        jsonReplay.setSensorValueToken(sensorValueToken);
        return jsonReplay;
    }

    @Override
    public void validate() {
        if(path.isEmpty() || path == null){
            addError(new IllegalArgumentException("The path must not be empty or null"));
        }else{
            File file = new File(path);
            if (!file.exists() || !file.canRead() || !file.isFile()) {
                addError(new IllegalArgumentException("The path must be a valid file"));
            }
            try{
                JsonParser parser = new JsonParser();
                JsonElement jsontree = parser.parse(new FileReader(path));
                JsonObject jo = jsontree.getAsJsonObject();
                JsonElement sensorname = jo.get(this.sensorNameToken);
                JsonArray record = jo.getAsJsonArray(this.sensorRecordToken);
                if (sensorname == null){
                    addError(new Exception("Wrong token : \"" + sensorNameToken + "\" for sensor name in file " + path));
                }
                if (record == null){
                    addError(new Exception("Wrong token : \"" + sensorRecordToken + "\" for sensor record in file" + path));
                }else{
                    for (Object o : record)
                    {
                        JsonObject sensor = (JsonObject) o;
                        JsonElement jsonvalue = sensor.get(sensorValueToken);
                        JsonElement jsontime = sensor.get(sensorRelativeTimeToken);
                        if(jsonvalue == null){
                            addError(new Exception("Wrong token : \"" + sensorValueToken + "\" for sensor value in file " + path));
                        }else{
                            try {
                                Double value = jsonvalue.getAsDouble();
                                if(value.isNaN()){
                                    addError(new Exception("Bad types values when trying to collect " + sensorValueToken + "fields"));
                                }
                            }catch (NumberFormatException ne){
                                addError(new Exception("Bad values format when collecting " + sensorValueToken + " fields"));
                            }

                        }
                        if(jsontime == null){
                            addError(new Exception("Wrong token : \"" + sensorRecordToken + "\" for sensor time in file " + path));
                        }

                    }
                }


            }catch (FileNotFoundException e){
                addError(new IllegalArgumentException("The file must exist"));
            }
        }

    }
}
