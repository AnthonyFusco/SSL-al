package dsl

abstract class SslBaseScript extends Script {

	def createLaw(String name) {
        [asFct: { String strategy -> ((SslBinding)getBinding()).getModel().createLaw(name, strategy)}]

	}



    def createSensorsSet(String name) {
        boolean error = true
        [sensorsNumber: { int n ->
            [withLaw : { String law ->
                ((SslBinding)getBinding()).getModel().createSensorsLot(name, n, law)
                error = false
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
