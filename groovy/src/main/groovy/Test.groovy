law "random1" ofType "Random"

law "markov1" ofType "MarkovChain" givenMatrix ([[0.9,0.075,0.025],[0.15,0.8,0.05],[0.25,0.25,0.5]])

replay "file1" fromPath "/home/ringo/Bureau/SSL-al/groovy/src/main/resources/rawdata/data1.csv" format "CSV" withColumns ([t: 0, s: 1, v: 8])

law "poly1" ofType "MathFunction" itReturns "INT" withExpressions (["0": "x<0.22"])

addNoise "file1", -5, 5
addOffset  "file1", 10

//sensorLot "parking" sensorsNumber 2 withLaw "random1" withDuration 10
//sensorLot "csv" sensorsNumber 2 withLaw "file1" withDuration 10
sensorLot "markov" sensorsNumber 2 withLaw "markov1" withDuration 10

runSimulation "fac"