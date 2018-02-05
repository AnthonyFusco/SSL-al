package dsl

import builders.LawBuilder
import builders.MarkovBuilder
import builders.MathFunctionBuilder
import builders.RandomBuilder
import builders.ReplayBuilder
import builders.SensorsLotBuilder
import kernel.structural.laws.LawType

abstract class SslBaseScript extends Script {

    def law(String name) {
        [ofType: { String typeKey ->
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
            ((SslBinding) getBinding()).getModel().addLawBuilder(builder)
            return builder
        }]
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

    def runSimulation(String name) {
        new Runner(((SslBinding) getBinding()).getModel()).runSimulation()
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
