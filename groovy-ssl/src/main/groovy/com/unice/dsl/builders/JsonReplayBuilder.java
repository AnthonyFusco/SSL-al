package com.unice.dsl.builders;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import kernel.datasources.executables.replay.JsonReplay;
import kernel.datasources.laws.DataSource;
import kernel.units.Duration;
import kernel.units.TimeUnit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class JsonReplayBuilder extends AbstractEntityBuilder<DataSource> {

    private String path;
    private Duration offset = new Duration(0, TimeUnit.Second);
    private List<Integer> noiseRange;

    private String sensorNameToken;
    private String sensorRecordToken;
    private String sensorValueToken;
    private String sensorRelativeTimeToken;

    private String defaultsensorNameToken = "bn";
    private String defaultsensorRecordToken = "e";
    private String defaultsensorValueToken = "v";
    private String defaultsensorRelativeTimeToken = "t";

    public JsonReplayBuilder(int definitionLine) {
        super(definitionLine);
    }

    public JsonReplayBuilder recordToken(String sensorRecordToken) {
        this.sensorRecordToken = sensorRecordToken;
        return this;
    }

    public JsonReplayBuilder valueToken(String sensorValueToken) {
        this.sensorValueToken = sensorValueToken;
        return this;
    }

    public JsonReplayBuilder timeToken(String sensorRelativeTimeToken) {
        this.sensorRelativeTimeToken = sensorRelativeTimeToken;
        return this;
    }

    public JsonReplayBuilder nameToken(String sensorNameToken) {
        this.sensorNameToken = sensorNameToken;
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
        jsonReplay.setIsExecutable(isExecutable());
        jsonReplay.setExecutableName(getExecutableName());

        jsonReplay.setSensorNameToken(sensorNameToken);
        jsonReplay.setSensorRecordToken(sensorRecordToken);
        jsonReplay.setSensorRelativeTimeToken(sensorRelativeTimeToken);
        jsonReplay.setSensorValueToken(sensorValueToken);

        if (sensorNameToken == null) {
            jsonReplay.setSensorNameToken(defaultsensorNameToken);
        }
        if (sensorRecordToken == null) {
            jsonReplay.setSensorRecordToken(defaultsensorRecordToken);
        }
        if (sensorRelativeTimeToken == null) {
            jsonReplay.setSensorRelativeTimeToken(defaultsensorRelativeTimeToken);
        }
        if (sensorValueToken == null) {
            jsonReplay.setSensorValueToken(defaultsensorValueToken);
        }
        return jsonReplay;
    }

    @Override
    public void validate() {

        if (sensorRelativeTimeToken == null) {
            addWarning("Time token not specified, using default \"t\" token");
        }
        if (sensorValueToken == null) {
            addWarning("Value token not specified, using default \"v\" token");
        }
        if (sensorRecordToken == null) {
            addWarning("Record token not specified, using default \"e\" token");
        }
        if (sensorNameToken == null) {
            addWarning("Sensor name token not specified, using default \"bn\" token");
        }
        if (noiseRange != null) {
            if (noiseRange.size() != 2) {
                addError(new IllegalArgumentException("You must specify only a lower and an upper limit to the noise range ex :[0, 10]"));
            } else {
                try {
                    Double inf = noiseRange.get(0).doubleValue();
                    Double sup = noiseRange.get(1).doubleValue();

                    if (noiseRange.get(0) > noiseRange.get(1)) {
                        addWarning("Your noise lower limit is greater than your upper limit, unexpected results can occur");
                    }
                } catch (ClassCastException nfe) {
                    addError(new IllegalArgumentException("You must use integer for your noise range"));
                }

            }
        }
        if (path.isEmpty() || path == null) {
            addError(new IllegalArgumentException("The path must not be empty or null"));
        } else {
            File file = new File(path);
            if (!file.exists() || !file.canRead() || !file.isFile()) {
                addError(new IllegalArgumentException("The path must be a valid file"));
            }

            try {
                JsonParser parser = new JsonParser();
                JsonElement jsontree = parser.parse(new FileReader(path));

                if (!(sensorValueToken == null && sensorRelativeTimeToken == null && sensorRecordToken == null && sensorNameToken == null)) {

                    JsonObject jo = jsontree.getAsJsonObject();
                    JsonElement sensorname = jo.get(this.sensorNameToken);
                    JsonArray record = jo.getAsJsonArray(this.sensorRecordToken);
                    if (sensorname == null) {
                        addError(new Exception("Wrong token : \"" + sensorNameToken + "\" for sensor name in file " + path));
                    }
                    if (record == null) {
                        addError(new Exception("Wrong token : \"" + sensorRecordToken + "\" for sensor record in file" + path));
                    } else {
                        for (Object o : record) {
                            JsonObject sensor = (JsonObject) o;
                            JsonElement jsonvalue = sensor.get(sensorValueToken);
                            JsonElement jsontime = sensor.get(sensorRelativeTimeToken);
                            if (jsonvalue == null) {
                                addError(new Exception("Wrong token : \"" + sensorValueToken + "\" for sensor value in file " + path));
                            } else {
                                try {
                                    Double value = jsonvalue.getAsDouble();
                                    if (value.isNaN()) {
                                        addError(new Exception("Bad types values when trying to collect " + sensorValueToken + "fields"));
                                    }
                                } catch (NumberFormatException ne) {
                                    addError(new Exception("Bad values format when collecting " + sensorValueToken + " fields"));
                                }
                                try {
                                    Double time = jsontime.getAsDouble();
                                } catch (NumberFormatException ne) {
                                    addError(new Exception("Bad values format when collecting " + sensorRelativeTimeToken + " fields"));

                                }

                            }
                            if (jsontime == null) {
                                addError(new Exception("Wrong token : \"" + sensorRecordToken + "\" for sensor time in file " + path));
                            }

                        }
                    }
                }

            } catch (FileNotFoundException e) {
                addError(new IllegalArgumentException("The file must exist"));
            }
        }

    }
}
