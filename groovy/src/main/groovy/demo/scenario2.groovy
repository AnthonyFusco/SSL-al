package demo

resetDB()

//law "parkingLaw" ofType MarkovLaw matrix([[0.3, 0.7], [0.15, 0.8, 0.05], [0.25, 0.3, 0.5]]) stateFrequency 1 / min

//dataSource "parkingLaw" ofType MarkovLaw matrix([[0.3, 0.7], [0.2, 0.8]]) stateFrequency 2 / h

parkingLaw "parkingLaw"

sensorLot "eurecom" sensorsNumber 2 law "parkingLaw" frequency 2 / h

String path = "/home/afusco/IdeaProjects/SSL-al/groovy/src/main/resources/rawdata/data1.csv"
//data1.csv
//notATime.csv

replay "Velo" path path columns([t: 0, s: 1, v: 8]) offset 10.s noise ([100, 200])

play "Velo", "eurecom"

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"