package demo

resetDB()

markov = markovLaw {
    matrix ([[0.5,0.5], [0.4, 0.6]])
    stateFrequency 1 / h
}

eurecom = sensorLot {
    sensorsNumber 5
    law markov
    frequency 10 / h
}

bike = jsonreplay {
    path "/home/afusco/IdeaProjects/SSL-al/groovy/src/main/resources/rawdata/bike.json"
    offset 10.s
    noise ([100,200])
}

//data1.csv
//notATime.csv
//replay "Velo" path path columns([t: 0, s: 1, v: 8]) offset 10.s noise ([100, 200])

play bike

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"