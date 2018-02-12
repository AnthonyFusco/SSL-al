package demo

resetDB()

law "parkingLaw" ofType MarkovLaw withMatrix([[0.3, 0.5], [0.15, 0.8, 0.05], [0.25, 0.3, 0.5]]) changeStateFrequency 1 / min

//law "parkingLaw" ofType MarkovLaw withMatrix([[0.3, 0.2, 0.5], [0.15, 0.8, 0.05], [0.25, 0.25, 0.5]]) changeStateFrequency 1 / min

sensorLot "eurecom" sensorsNumber 1 withLaw "parkingLaw" withFrequency 1 / min

sensorLot "lucioles" sensorsNumber 10 withLaw "parkingLaw" withFrequency 1 / 5.min

String path = "/home/afusco/IdeaProjects/SSL-al/groovy/src/main/resources/rawdata/data1.csv"
replay "Velo" fromPath path withColumns([t: 0, s: 1, v: 8]) withOffset 10.s

runSimulation "fac", "10/02/2018 09:25:00", "10/02/2018 10:30:00"