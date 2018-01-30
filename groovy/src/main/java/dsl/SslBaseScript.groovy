package dsl

import exceptions.MethodCallException
import kernel.structural.laws.Law
import kernel.structural.laws.LawType
import kernel.structural.laws.MarkovChainLaw

abstract class SslBaseScript extends Script {

	def law(String name) {
        [ofType : { String typeKey ->
            LawType lawType = LawType.valueOf(typeKey)
            Law law = ((SslBinding) getBinding()).getModel().createLaw(name, lawType)
            /*if (lawType == LawType.Random) {

            } else if (lawType == LawType.MarkovChain) {

            }*/
            [withColumns: { Map<String, Integer> map ->
                if (lawType != LawType.File) {
                    throw new MethodCallException(lawType.toString() + " does not allow withColumns declaration")
                }

            },
            states: { List<String> listStates ->
                if (lawType != LawType.MarkovChain) {
                    throw new MethodCallException(lawType.toString() + " does not allow states declaration")
                }
                for (String state : listStates) {
                    ((MarkovChainLaw) law).declareState(state)
                }

                def closure
                closure = { String originState ->
                    [to : { String targetState ->
                        [with : { double p ->
                            ((MarkovChainLaw) law).addTransition(originState, targetState, p)
                            [and: closure]
                        }]
                    }]
                }
                [transitions: closure]
            }]
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
		((SslBinding)getBinding()).getModel().runSimulation(name)
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
