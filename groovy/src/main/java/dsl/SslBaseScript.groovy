package dsl

import exceptions.MethodCallException
import kernel.behavioral.DataSourceType
import kernel.structural.SensorsLot
import kernel.structural.laws.FileLaw

import kernel.structural.laws.Law
import kernel.structural.laws.LawType
import kernel.structural.laws.MarkovChainLaw

abstract class SslBaseScript extends Script {

    def law(String name) {
        [ofType : { String typeKey ->
            LawType lawType = LawType.valueOf(typeKey)
            Law law = ((SslBinding) getBinding()).getModel().createLaw(name, lawType)
            [fromPath : { String path ->
                if (lawType != LawType.File) {
                    throw new MethodCallException(lawType.toString() + " does not allow fromPath declaration")
                }
                ((FileLaw) law).setPath(path)
                [format: { String formatName ->
                    DataSourceType dataSourceType = DataSourceType.valueOf(formatName)
                    ((FileLaw) law).setDataSourceType(dataSourceType)
                    [withColumns: { Map<String, Integer> map ->
                        if (dataSourceType != DataSourceType.CSV) {
                            throw new MethodCallException(dataSourceType.toString() + " does not allow withColumns declaration")
                        }
                        ((FileLaw) law).setColumnsDescriptions(map)
                    },
                    withProperties: { Map<String, String> map ->
                        if (dataSourceType != DataSourceType.JSON) {
                            throw new MethodCallException(dataSourceType.toString() + " does not allow withProperties declaration")
                        }
                        ((FileLaw) law).setColumnsDescriptions(map)
                    }]
                }]
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

    def addNoise(String lawTarget, int inf, int sup) {
        //TODO apply noise to law
    }

    def addOffset(String lawTarget, int offset) {
        //TODO apply offset to law
    }

    def sensorLot(String name) {
		[sensorsNumber: { int n ->
			[withLaw : { String law ->
                SensorsLot lot = ((SslBinding)getBinding()).getModel().createSensorsLot(name, n, law)
                [withDuration : { int t ->
                    lot.setSimulationDuration(t)
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
