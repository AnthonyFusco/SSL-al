law "random1" ofType "Random"

law "markov1" ofType "MarkovChain" states (["state1", "state2"]) transitions "state1" to "state2" with 0.2 and "state2" to "state1" with 0.7

sensorLot "parking" sensorsNumber 50 withLaw "random1" withDuration 10

runSimulation "fac"