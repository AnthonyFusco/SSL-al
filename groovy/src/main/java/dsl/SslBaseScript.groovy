package dsl

abstract class SslBaseScript extends Script {

	def law(String name) {
        [ofType : { String lawType ->
            ((SslBinding)getBinding()).getModel().createLaw(name, lawType)
        }]

	}

    def sensorLot(String name) {
		[sensorsNumber: { int n ->
			[withLaw : { String law ->
                [withDuration : { int t ->
                    ((SslBinding)getBinding()).getModel().createSensorsLot(name, n, law, t)
                }]
			}]
		}]
    }

	def runSimulation(String name) {
		((SslBinding)getBinding()).getModel().runSimulation(name);
	}

	int count = 0
	abstract void scriptBody()
	def run() {
		if(count == 0) {
			count++
			scriptBody()
		} else {
			println "Run method is disabled"
		}
	}
}
