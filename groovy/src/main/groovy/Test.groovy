law "random1" ofType "Random"


law "markov1" ofType "MarkovChain" states (["state1", "state2"]) transitions "state1" to "state2" with 0.2 and "state2" to "state1" with 0.7

law "file1" ofType "File" fromPath "/home/afusco/Cours/security/SSL-al/groovy/src/main/resources/rawdata/data1.csv" format "CSV" withColumns ([t: 0, s: 1, v: 8])

addNoise "file1", -5, 5
addOffset  "file1", 10

//sensorLot "parking" sensorsNumber 50 withLaw "random1" withDuration 10
sensorLot "csv" sensorsNumber 2 withLaw "file1" withDuration 10

runSimulation "fac"