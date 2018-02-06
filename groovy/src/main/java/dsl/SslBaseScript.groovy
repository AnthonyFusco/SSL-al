package dsl

import builders.LawBuilder
import builders.MarkovBuilder
import builders.MathFunctionBuilder
import builders.RandomBuilder
import builders.ReplayBuilder
import builders.SensorsLotBuilder
import kernel.structural.laws.Law
import kernel.structural.laws.LawType

import java.text.DateFormat
import java.text.SimpleDateFormat

abstract class SslBaseScript extends Script {

    def law(String name) {
        [ofType: { String typeKey ->
            LawBuilder<? extends Law> builder = lawFactory(name, typeKey)
            ((SslBinding) getBinding()).getModel().addLawBuilder(builder)
            return builder
        }]
    }

    static LawBuilder<? extends Law> lawFactory(String name, String typeKey) {
        LawType lawType = LawType.valueOf(typeKey)
        LawBuilder builder
        switch (lawType) {
            case LawType.Random:
                builder = new RandomBuilder(name)
                break
            case LawType.MarkovChain:
                builder = new MarkovBuilder(name)
                break
            case LawType.MathFunction:
                builder = new MathFunctionBuilder(name)
                break
        }
        return builder
    }

    def replay(String name) {
        return new ReplayBuilder(name)
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
        DateFormat format = new SimpleDateFormat("dd/mm/yyyy:hh.mm", Locale.ENGLISH)
        Date startDate = format.parse(startDateString)
        Date endDate = format.parse(endDateString)

//        println(startDate.toInstant().toEpochMilli())
//        println(endDate.toInstant().toEpochMilli())
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
