package demo

resetDB()

def markov = markovLaw {
    matrix ([[0.5,0.5], [0.4, 0.6]])
    stateFrequency 1 / h
}

def eurecom = sensorLot {
    sensorsNumber 5
    law markov
    frequency 10 / h
}

play bike

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"