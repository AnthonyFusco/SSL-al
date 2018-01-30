package dsl

abstract class SslBaseScript extends Script {

	def createLaw(String name) {
        [asFct: { String strategy -> ((SslBinding)getBinding()).getModel().createLaw(name, strategy)}]
	}

    def createSensorsSet(String name) {
		[sensorsNumber: { int n ->
			[withLaw : { String law ->
                [withDuration : { int t ->
                    ((SslBinding)getBinding()).getModel().createSensorsLot(name, n, law, t)
                }]
			}]
		}]
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
