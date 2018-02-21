package dsl

import builders.CompositeBuilder
import builders.RandomBuilder
import builders.ReplayBuilder
import builders.SensorsLotBuilder
import groovy.transform.BaseScript
import kernel.structural.EntityBuilder
import kernel.structural.laws.DataSource
import org.influxdb.InfluxDB
import org.influxdb.InfluxDBFactory

import java.text.DateFormat
import java.text.SimpleDateFormat

abstract class SslBaseScript extends Script {

    def resetDB() {
        InfluxDB influxDB = InfluxDBFactory.connect("http://localhost:8086", "root", "root")
        String dbName = "influxdb"
        influxDB.deleteDatabase(dbName)
        influxDB.createDatabase(dbName)
    }

    def play(EntityBuilder<DataSource>... vars) {
        for (EntityBuilder<DataSource> var : vars) {
            var.setExecutable(true)
        }
    }

    def composite(Closure closure) {
        CompositeBuilder builder = new CompositeBuilder<>()
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        def code = closure.rehydrate(builder, this, this)
        code()
        builder
    }

    def parkingComposite() {
        composite({
            filter({x -> x == x}).map({x -> x})
            reduce({res, sensor -> res + sensor})
            withFrequency(2 / h)
        })
    }

    def randomLaw(Closure closure) {
        RandomBuilder builder = new RandomBuilder()
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        def code = closure.rehydrate(builder, this, this)
        //code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        builder
    }

    def replay(Closure closure) {
        def builder = new ReplayBuilder()
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        def code = closure.rehydrate(builder, this, this)
        code()
        builder
    }

    def sensorLot(Closure closure) {
        int line = this.getBinding().getVariable(ScriptTransformer.LINE_COUNT_VARIABLE_NAME)
        def builder = new SensorsLotBuilder()
        ((SslBinding) getBinding()).getModel().addDataSourcesBuilder(builder)
        def code = closure.rehydrate(builder, this, this)
        code()
        builder
    }

    def runSimulation(String startDateString, String endDateString) {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.ENGLISH)
        Date startDate = format.parse(startDateString)
        Date endDate = format.parse(endDateString)

        return new Runner(((SslBinding) getBinding()).getModel()).runSimulation(startDate, endDate)
    }

    int count = 0

    abstract void scriptBody()

    def run() {
        if (count == 0) {
            count++
            scriptBody()
        } else {
            println "Run method is disabled"
        }
    }
}

