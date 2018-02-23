package demo

resetDB()


def eurecomMarkov = markovLaw {
    matrix ([[0.7,0.3], [0.3, 0.7]])
    stateFrequency 1 / h
}

def eurecom = sensorLot {
    sensorsNumber 5
    law eurecomMarkov
    frequency 2 / h
}

def valrose = sensorLot {
    sensorsNumber 5
    law markovLaw {
        matrix([[0.9, 0.1], [0.1, 0.9]])
        stateFrequency 2 / h
    }
    frequency 1 / h
}

play valrose, eurecom

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"