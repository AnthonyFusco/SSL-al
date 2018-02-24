package demo

createOrResetDB("influxdb")

markov = markovLaw {
    matrix([[0.5, 0.5], [0.5, 0.5]])
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

play valroseComposite

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"