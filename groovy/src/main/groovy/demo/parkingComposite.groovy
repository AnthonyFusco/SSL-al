package demo

createOrResetDB()

//a simple simulation of a parking
valroseTop = sensorLot {
    sensorsNumber 5
    law markovLaw {
        matrix([[0.9, 0.1], [0.1, 0.9]])
        stateFrequency 2 / h
    }
    frequency 2 / h
}

//a simple simulation of a parking
valroseMiddle = sensorLot {
    sensorsNumber 15
    law markovLaw {
        matrix([[0.8, 0.2], [0.3, 0.7]])
        stateFrequency 1 / h
    }
    frequency 1 / h
}

//a composite using two parkings (valroseTop, valroseMiddle) and one created on the fly
valroseComposite = composite {
    withSensors([
            valroseTop,
            valroseMiddle,
            sensorLot {
                law markovLaw {
                    matrix([[0.7, 0.3], [0.3, 0.7]])
                    stateFrequency 2 / h
                }
            }
    ])
    filter({ x -> x == x })
    map({ x -> x })
    reduce({ res, sensor -> res + sensor })
    frequency 2 / h
}

//a simple simulation of a parking
eurecomTop = sensorLot {
    sensorsNumber 3
    law markovLaw {
        matrix([[0.6, 0.4], [0.1, 0.9]])
        stateFrequency 2 / h
    }
    frequency 2 / h
}

//a simple simulation of a parking
eurecomBottom = sensorLot {
    sensorsNumber 7
    law markovLaw {
        matrix([[0.8, 0.2], [0.3, 0.7]])
        stateFrequency 1 / h
    }
    frequency 1 / h
}

//a composite using two parkings (eurecomTop, eurecomBottom) and a composite create before (valroseComposite)
fac = composite {
    withSensors([valroseComposite, eurecomTop, eurecomBottom])
    filter({ x -> x == x })
    map({ x -> x })
    reduce({ res, sensor -> res + sensor })
    frequency 2 / h
}

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play fac
}