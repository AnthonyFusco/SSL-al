law "random1" ofType "Random"

law "markov1" ofType "MarkovChain" states (["state1", "state2"]) transitions "state1" to "state2" with 0.2 and "state2" to "state1" with 0.7

law "file1" ofType "File" fromPath "url://path" format "CSV" withColumns ([t: 1, s: 2, v: 3])

addNoise( "file1", [-5,5])
addOffset( "file1", 10)

sensorLot "parking" sensorsNumber 50 withLaw "random1" withDuration 10

runSimulation "fac"