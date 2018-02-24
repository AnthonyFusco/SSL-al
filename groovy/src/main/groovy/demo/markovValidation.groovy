package demo

createOrResetDB("influxdb")

markov = markovLaw {
    matrix([[0.5, 0.5, 0.5], [0.5, 0.6]])
    stateFrequency 1 / h
}

eurecom = sensorLot {
    sensorsNumber 5
    law markov
}

play eurecom

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"