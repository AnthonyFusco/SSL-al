package demo

//We haven't been able to prevent recursion inside of a filter/map/reduce closure

createOrResetDB()

markov = markovChain {
    matrix([[0.5, 0.4], [0.4, 0.5]])
    stateFrequency 1 / h
}

valroseTop = sensorLot {
    sensorsNumber 5
    law markov
    frequency 2 / h
}

valroseMiddle = sensorLot {
    sensorsNumber 15
    law markov
    frequency 1 / h
}

valroseComposite = composite {
    withSensors([
            valroseTop,
            valroseMiddle,
            markov
    ])
}

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play valroseComposite
}