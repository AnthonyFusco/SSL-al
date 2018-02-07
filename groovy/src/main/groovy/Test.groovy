law "random1" ofType RandomLaw

law "markov1" ofType MarkovLaw givenMatrix ([[0.3,0.2,0.5],[0.15,0.8,0.05],[0.25,0.25,0.5]])

replay "file1" fromPath "/home/ringo/Bureau/SSL-al/groovy/src/main/resources/rawdata/data1.csv" format "CSV" withColumns ([t: 0, s: 1, v: 8])

law "poly1" ofType FunctionLaw itReturns "INT" withExpressions (["0": "x>0.22","22" : "x<0.22"])
law "poly2" ofType FunctionLaw itReturns "STRING" withExpressions (["Shiny": "x>0.22","Rainy" : "x<0.22"])


addNoise "file1", -5, 5
addOffset  "file1", 10

//sensorLot "parking" sensorsNumber 2 withLaw "random1" withDuration 10
//sensorLot "csv" sensorsNumber 2 withLaw "file1" withDuration 10
sensorLot "markov" sensorsNumber 2 withLaw "markov1" withDuration 10 withFrequency 5/s //eq 5/2.s
sensorLot "polypoly1" sensorsNumber 2 withLaw "poly1" withDuration 10
sensorLot "polypoly2" sensorsNumber 2 withLaw "poly2" withDuration 10

//sensorLot "markov" sensorsNumber 2 withLaw "markov1" withDuration 10
sensorLot "random" sensorsNumber 2 withLaw "random1" withDuration 10

runSimulation "fac", "06/02/2018:14.56", "06/02/2018:14.58"