package dsl

import builders.*
import kernel.structural.EntityBuilder
import kernel.structural.laws.DataSource
import org.influxdb.InfluxDB
import org.influxdb.InfluxDBFactory

import java.text.DateFormat
import java.text.SimpleDateFormat

abstract class SslBaseScript extends Script {

    def composite(Closure closure) {
        CompositeBuilder builder = new CompositeBuilder<>(getCurrentLine())
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        rehydrateClosureWithBuilder(closure, builder)
        builder
    }

    def randomLaw(Closure closure) {
        RandomBuilder builder = new RandomBuilder(getCurrentLine())
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        rehydrateClosureWithBuilder(closure, builder)
        builder
    }


    def markovLaw(Closure closure) {
        MarkovBuilder builder = new MarkovBuilder(getCurrentLine())
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        rehydrateClosureWithBuilder(closure, builder)
        builder
    }

    def mathFunction(Closure closure) {
        MathFunctionBuilder builder = new MathFunctionBuilder(getCurrentLine())
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        rehydrateClosureWithBuilder(closure, builder)
        builder
    }

    def jsonreplay(Closure closure) { //todo abstract the builders for json and csv
        def builder = new JsonReplayBuilder(getCurrentLine())
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        rehydrateClosureWithBuilder(closure, builder)
        builder
    }

    def replay(Closure closure) {
        def builder = new ReplayBuilder(getCurrentLine())
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        rehydrateClosureWithBuilder(closure, builder)
        builder
    }

    def sensorLot(Closure closure) {
        def builder = new SensorsLotBuilder(getCurrentLine())
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        rehydrateClosureWithBuilder(closure, builder)
        builder
    }

    def play(EntityBuilder<DataSource>... vars) {
        for (EntityBuilder<DataSource> var : vars) {
            var.setExecutable(true)
            getBinding().getVariables().entrySet().stream()
                    .filter({ entry -> entry.value == var })
                    .map({ entry -> entry.key })
                    .findFirst()
                    .ifPresent({ name -> var.setExecutableName(name.toString()) })
        }
    }

    def runSimulation(String startDateString, String endDateString) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
        Date startDate = format.parse(startDateString)
        Date endDate = format.parse(endDateString)
        new Runner(((SslBinding) getBinding()).getModel()).runSimulation(startDate, endDate)
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

    def createOrResetDB(String name) { //do not put static
        if (name == null) {
            name = "influxdb"
        }
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root")
        String dbName = name
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
                        "Keyword \"" + mme.getMethod() + mme.printStackTrace() + "\" not recognized, misspelled or wrong(s) parameter(s)" +
                        "\u001B[37m"
            }
        } else {
            println "Run method is disabled"
        }
    }
}