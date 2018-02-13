package demo

resetDB()

//law "parkingLaw" ofType MarkovLaw withMatrix([[0.3, 0.5], [0.15, 0.8, 0.05], [0.25, 0.3, 0.5]]) changeStateFrequency 1 / min

//law "parkingLaw" ofType MarkovLaw withMatrix([[0.7, 0.3], [0.8, 0.2]]) changeStateFrequency 2 / h

//parkingLaw "parkingLaw"

//sensorLot "eurecom" sensorsNumber 1 withLaw "parkingLaw" withFrequency 2 / h

//sensorLot "lucioles" sensorsNumber 10 withLaw "parkingLaw" withFrequency 2 / h
String path = "/home/ringo/Bureau/SSL-al/groovy/src/main/resources/rawdata/data1.csv"
replay "Velo" fromPath path withColumns([t: 0, s: 1, v: 8]) withOffset 10.s withNoise ([100,200])


runSimulation "fac", "10/02/2018 08:00:00", "10/02/2018 09:00:00"