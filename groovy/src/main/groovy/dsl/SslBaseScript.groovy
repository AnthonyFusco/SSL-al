package dsl

import builders.*
import kernel.datasources.laws.DataSource
import org.influxdb.InfluxDB
import org.influxdb.InfluxDBFactory

import java.text.DateFormat
import java.text.SimpleDateFormat

abstract class SslBaseScript extends Script {

    def methodMissing(String name, def args) {
        def closure = args[0] as Closure
        switch (name) {
            case "composite":
                return handleBuilder(new CompositeBuilder<>(getCurrentLine()), closure)
            case "randomLaw":
                return handleBuilder(new RandomBuilder<>(getCurrentLine()), closure)
            case "markovLaw":
                return handleBuilder(new MarkovBuilder<>(getCurrentLine()), closure)
            case "mathFunction":
                return handleBuilder(new MathFunctionBuilder<>(getCurrentLine()), closure)
            case "jsonReplay":
                return handleBuilder(new JsonReplayBuilder<>(getCurrentLine()), closure)
            case "csvReplay":
                return handleBuilder(new ReplayBuilder<>(getCurrentLine()), closure)
            case "sensorLot":
                return handleBuilder(new SensorsLotBuilder<>(getCurrentLine()), closure)
            default:
                println "\u001B[31m" +
                        "method " + name + " not recognized method does not exists or is misspelled " +
                        ".(" + Runner.currentFile + ":" + getCurrentLine() + ")" +
                        "\u001B[37m"
        }
    }

    def play(EntityBuilder<DataSource>... builders) {
        for (EntityBuilder<DataSource> builder : builders) {
            builder.setExecutable(true)
            getBinding().getVariables().entrySet().stream()
                    .filter({ entry -> entry.value == builder })
                    .map({ entry -> entry.key })
                    .findFirst()
                    .ifPresent({ name -> builder.setExecutableName(name.toString()) })
        }
    }

    def runSimulation(String startDateString, String endDateString) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
        Date startDate = format.parse(startDateString)
        Date endDate = format.parse(endDateString)
        new Runner(((SslBinding) getBinding()).getModel()).runSimulation(startDate, endDate)
    }

    private AbstractEntityBuilder<DataSource> handleBuilder(AbstractEntityBuilder<DataSource> builder, Closure closure) {
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        rehydrateClosureWithBuilder(closure, builder)
        return builder
    }

    private void rehydrateClosureWithBuilder(Closure closure, AbstractEntityBuilder<DataSource> builder) {
        def code = closure.rehydrate(builder, this, this)
        try {
            code()
        } catch (MissingMethodException mme) {
            builder.addError(new Exception("Keyword \"" + mme.getMethod() + "\"" +
                    "not recognized, misspelled or wrong(s) parameter(s)"))
        }
    }

    def createOrResetDB() { //do not put static
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root")
        String dbName = "influxdb"
        influxDB.deleteDatabase(dbName)
        influxDB.createDatabase(dbName)
    }

    private int getCurrentLine() {
        this.getBinding().getVariable(ScriptTransformer.LINE_COUNT_VARIABLE_NAME) as int
    }

    int count = 0

    abstract void scriptBody()

    def run() {
        if (count == 0) {
            count++
            try {
                scriptBody()
            } catch (MissingPropertyException e) {
                println "\u001B[31m" +
                        e.getMessageWithoutLocationText().replace("Script1", getProperty("name").toString()) +
                        "\u001B[37m"
            } catch (MissingMethodException mme) {
                println "\u001B[31m" +
                        "Keyword \"" + mme.getMethod() + mme.printStackTrace() +
                        "\" not recognized, misspelled or wrong(s) parameter(s)" +
                        "\u001B[37m"
            }
        } else {
            println "Run method is disabled"
        }
    }
}