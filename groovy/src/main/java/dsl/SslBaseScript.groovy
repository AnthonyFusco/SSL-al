package dsl

abstract class SslBaseScript extends Script {

    private SslBinding getSslBinding() {
        return (SslBinding)getBinding()
    }

	def sensorsLot(String name) {
        println name
        getSslBinding().getModel().createSensorsLot(name)
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
