//simulation of parking lots with markov chains

createOrResetDB()

eurecomMarkov = markovChain {
    matrix([[0.7, 0.3], [0.3, 0.7]])
    stateFrequency 2 / h
}

eurecom = sensorLot {
    sensorsNumber 5
    law eurecomMarkov
    frequency 2 / h
}

valrose = sensorLot {
    sensorsNumber 5
    law markovChain {
        matrix([[0.9, 0.1], [0.1, 0.9]])
        stateFrequency 2 / h
    }
    frequency 2 / h
}

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play valrose, eurecom
}