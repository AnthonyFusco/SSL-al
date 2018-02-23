package demo

resetDB()


eurecomMarkov = markovLaw {
    matrix ([[0.9,0.1], [0.2,0.8]])
    stateFrequency 2 / h
}

eurecom = sensorLot {
    sensorsNumber 5
    law eurecomMarkov
    frequency 2 / h
}

valrose = sensorLot {
    sensorsNumber 5
    law markovLaw {
        matrix([[0.9, 0.1], [0.1, 0.9]])
        stateFrequency 2 / h
    }
    frequency 2 / h
}

play valrose, eurecom

runSimlation "10/02/2018 08:00:00", "10/02/2018 19:00:00"