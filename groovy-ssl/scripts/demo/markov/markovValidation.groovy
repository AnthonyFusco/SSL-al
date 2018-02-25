//all markov validation

createOrResetDB()

markov = markovChain {
    matrix([[0.5, 0.5, 0.5], [0.5, 0.6]])
    stateFrequency 1 / h
}

eurecom = sensorLot {
    sensorsNumber 5
    law markov
}

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play eurecom
}