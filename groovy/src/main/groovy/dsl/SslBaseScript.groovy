package dsl

import builders.LawBuilder
import builders.MarkovBuilder
import builders.MathFunctionBuilder
import builders.RandomBuilder
import builders.ReplayBuilder
import builders.SensorsLotBuilder
import kernel.structural.laws.Law
import kernel.structural.laws.LawType
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

    def randomLaw(String name) {
        law(name).ofType(LawType.RandomLaw.toString())
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

    def addNoise(String lawTarget, int inf, int sup) {
        //TODO apply noise to law
    }

    def addOffset(String lawTarget, int offset) {
        //TODO apply offset to law
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
