package demo

createOrResetDB()

markovLot = sensorLot {
    sensorsNumber 5
    law markovLaw {
        matrix([[0.8, 0.1, 0.1], [0.1, 0.7, 0.2], [0.2, 0.2, 0.6]])
        stateFrequency 2 / h
    }
    frequency 2 / h
}

f = mathFunction {
    expression { t -> t == 0 ? 42 : t * 2 + 1 }
}

functionLot = sensorLot {
    sensorsNumber 2
    law f
    frequency 10 / h
}

sprint = csvReplay {
    path "datafiles/data1.csv"
    offset 1.h
    columns([v: 8])
    noise([0.01, 0.15])
}

composite = composite {
    withSensors([markovLot, functionLot, sprint])
    filter({ x -> x == x })
    map({ x -> x == 0 ? 10 : x })
    reduce({ res, sensor -> res / sensor })
    frequency 2 / h
}

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play composite
}