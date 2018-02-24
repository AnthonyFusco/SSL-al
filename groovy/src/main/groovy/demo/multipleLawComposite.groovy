package demo

resetDB()

markovLot = sensorLot {
    sensorsNumber 5
    law markovLaw {
        matrix([[0.8, 0.1, 0.1], [0.1, 0.7, 0.2], [0.2, 0.2, 0.6]])
        stateFrequency 2 / h
    }
    frequency 2 / h
}


functionLot = mathFunction {
    expression { t -> t * 2 + 1 }
}

composite = composite {
    withLots([markovLot, functionLot])
    map({ x -> x })
    reduce({ res, sensor -> res + sensor })
    frequency 2 / h
}

play composite

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"