package com.unice.dsl.engine

import com.unice.dsl.builders.*
import kernel.datasources.laws.DataSource
import org.influxdb.InfluxDB
import org.influxdb.InfluxDBException
import org.influxdb.InfluxDBFactory

abstract class SslBaseScript extends Script {

    def methodMissing(String name, def args) {
        def closure = args[0] as Closure
        switch (name) {
            case "composite":
                return handleBuilder(new CompositeBuilder<>(getCurrentLine()), closure)
            case "randomLaw":
                return handleBuilder(new RandomBuilder<>(getCurrentLine()), closure)
            case "markovChain":
                return handleBuilder(new MarkovBuilder<>(getCurrentLine()), closure)
            case "mathFunction":
                return handleBuilder(new MathFunctionBuilder<>(getCurrentLine()), closure)
            case "jsonReplay":
                return handleBuilder(new JsonReplayBuilder<>(getCurrentLine()), closure)
            case "csvReplay":
                return handleBuilder(new ReplayBuilder<>(getCurrentLine()), closure)
            case "sensorLot":
                return handleBuilder(new SensorsLotBuilder<>(getCurrentLine()), closure)
            case "simulate":
                return buildRunner(closure)
            default:
                printError("method " + name + " not recognized method does not exists or is misspelled " +
                        ".(" + Runner.currentFile + ":" + getCurrentLine() + ")")
        }
    }

    private AbstractEntityBuilder handleBuilder(AbstractEntityBuilder builder, Closure closure) {
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        rehydrateClosureWithBuilder(closure, builder)
        return builder
    }

    private void buildRunner(Closure closure) {
        def app = new ApplicationBuilder()
        rehydrateClosureWithBuilder(closure, app)
        for (EntityBuilder<DataSource> builder : app.getBuilders()) {
            builder.setExecutable(true)
            getBinding().getVariables().entrySet().stream()
                    .filter({ entry -> entry.value == builder })
                    .map({ entry -> entry.key })
                    .findFirst()
                    .ifPresent({ name -> builder.setExecutableName(name.toString()) })
        }
        if (app.getBuilders() == null) {
            System.out.println("\u001B[33m" + "nothing to play" + "\u001B[37m")
        }
        if (app.getStartDate() == null || app.getEndDate() == null) {
            printError("You need to specify a start date and an end date of the format dd/MM/yyyy HH:mm:ss")
            return
        }
        if (app.getStartDate() >= app.getEndDate()) {
            printError("The start date must be > to the end date")
            return
        }
        new Runner(((SslBinding) getBinding()).getModel())
                .runSimulation(app.getStartDate(), app.getEndDate())
    }

    private void rehydrateClosureWithBuilder(Closure closure, Object builder) {
        def code = closure.rehydrate(builder, this, this)
        try {
            code()
        } catch (MissingMethodException mme) {
            printError("Keyword " + mme.getMethod() + " not recognized, misspelled or wrong(s) parameter(s)")
        }
    }

    def createOrResetDB() { //do not make static
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root")
        String dbName = "influxdb"
        influxDB.deleteDatabase(dbName)
        influxDB.createDatabase(dbName)
    }

    private int getCurrentLine() {
        this.getBinding().getVariable(ScriptTransformer.LINE_COUNT_VARIABLE_NAME) as int
    }

    private void printError(String message) {
        println("\u001B[31m" + message + "\u001B[37m")
    }

    int count = 0

    abstract void scriptBody()

    def run() {
        if (count == 0) {
            count++
            try {
                scriptBody()
            } catch (MissingPropertyException e) {
                printError(e.getMessageWithoutLocationText().replace("Script1", getProperty("name").toString()))
            } catch (MissingMethodException mme) {
                printError("Keyword " + mme.getMethod() + mme.printStackTrace() +
                        " not recognized, misspelled or wrong(s) parameter(s)")
            } catch (InfluxDBException ignore) {
                printError("Error while sending messages to influxDB, " +
                        "some functions might not cover the entire range of time values, or influxDB isn't up")
            }
        } else {
            println "Run method is disabled"
        }
    }
}