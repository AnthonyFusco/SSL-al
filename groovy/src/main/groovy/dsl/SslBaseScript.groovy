package dsl

import builders.CompositeBuilder
import builders.LawBuilder
import builders.MarkovBuilder
import builders.MathFunctionBuilder
import builders.RandomBuilder
import builders.ReplayBuilder
import builders.SensorsLotBuilder
import kernel.structural.laws.Law
import kernel.structural.laws.LawType
import kernel.units.Duration
import kernel.units.Frequency
import kernel.units.TimeUnit
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

    def law(String name) {
        [ofType: { String typeKey ->
            LawBuilder<? extends Law> builder = lawFactory(name, typeKey)
            ((SslBinding) getBinding()).getModel().addLawBuilder(builder)
            return builder
        }]
    }

    def composite(String name) {
        CompositeBuilder builder = new CompositeBuilder<>(name)
        ((SslBinding) getBinding()).getModel().addCompositeBuilder(builder)
        return builder
    }

    //composite "eurecom" withLots (["top", "bot"]) filter({x -> x == x}) map({x -> x}) reduce({res, sensor -> res + sensor}) withFrequency 2 / h
    def parkingComposite(String name) {
        CompositeBuilder builder = composite(name)
        builder.filter({x -> x == x}).map({x -> x}).reduce({res, sensor -> res + sensor}).withFrequency(2 / h)
        return builder
    }

    def randomLaw(String name) {
        RandomBuilder builder = law(name).ofType(LawType.RandomLaw.toString())
        builder.withinRange(([0,10]))
    }

    def parkingLaw(String name) {
        MarkovBuilder builder = (MarkovBuilder)law(name).ofType(LawType.MarkovLaw.toString())
        builder.changeStateFrequency(new Frequency(2, new Duration(1, TimeUnit.Hour)))
        builder.withMatrix([[0.7, 0.3], [0.8, 0.2]])
        return builder
    }

    static LawBuilder<? extends Law> lawFactory(String name, String typeKey) {
        LawType lawType = LawType.valueOf(typeKey)
        LawBuilder builder
        switch (lawType) {
            case LawType.RandomLaw:
                builder = new RandomBuilder(name)
                break
            case LawType.MarkovLaw:
                builder = new MarkovBuilder(name)
                break
            case LawType.FunctionLaw:
                builder = new MathFunctionBuilder(name)
                break
        }
        return builder
    }

    def replay(String name) {
        ReplayBuilder replayBuilder = new ReplayBuilder(name)
        ((SslBinding) getBinding()).getModel().addReplayBuilder(replayBuilder)
        return replayBuilder
    }

    def sensorLot(String name) {
        SensorsLotBuilder builder = new SensorsLotBuilder(name)
        ((SslBinding) getBinding()).getModel().addSensorsBuilder(builder)
        return builder
    }

    def runSimulation(String name, String startDateString, String endDateString) {
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
