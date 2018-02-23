package demo

resetDB()

valroseTop = sensorLot {
    sensorsNumber 5
    law markovLaw {
        matrix([[0.9, 0.1], [0.1, 0.9]])
        stateFrequency 2 / h
    }
    frequency 2 / h
}

valroseBottom = sensorLot {
    sensorsNumber 10
    law markovLaw {
        matrix([[0.7, 0.3], [0.3, 0.7]])
        stateFrequency 2 / h
    }
    frequency 2 / h
}

valrose = composite {
    withLots([valroseTop, valroseBottom])
    filter({x -> x == x})
    map({x -> x})
    reduce({res, sensor -> res + sensor})
    frequency 2 / h
}

play valrose

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"