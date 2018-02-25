// Scenario regrouping many simulation

createOrResetDB()

//First a parking occupation simulation

//a simple simulation of a parking
valroseTop = sensorLot {
    sensorsNumber 5
    law markovChain {
        matrix([[0.9, 0.1], [0.1, 0.9]])
        stateFrequency 2 / h
    }
    frequency 2 / h
}

//a simple simulation of a parking
valroseMiddle = sensorLot {
    sensorsNumber 15
    law markovChain {
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
                law markovChain {
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
    law markovChain {
        matrix([[0.6, 0.4], [0.1, 0.9]])
        stateFrequency 2 / h
    }
    frequency 2 / h
}

//a simple simulation of a parking
eurecomBottom = sensorLot {
    sensorsNumber 7
    law markovChain {
        matrix([[0.8, 0.2], [0.3, 0.7]])
        stateFrequency 1 / h
    }
    frequency 1 / h
}

//a composite using two parkings (eurecomTop, eurecomBottom) and a composite create before (valroseComposite)
facParking = composite {
    withSensors([valroseComposite, eurecomTop, eurecomBottom])
    filter({ x -> x == x })
    map({ x -> x })
    reduce({ res, sensor -> res + sensor })
    frequency 2 / h
}

//A Temperature simulation

SensorTempExt = mathFunction {
    expression { t ->
        if (t < 7) 10
        else if (t < 20) 20
        else if (t < 30) 22
        else if (t < 40) 20
        else 14
    }
    noise([-3, 3])
}

ext = sensorLot {
    sensorsNumber 1
    law SensorTempExt
    frequency 1 / 10.min
}

//A Replay of a sonar

longTrip = csvReplay {
    path "datafiles/data2.csv"
    columns([t: 0, s: 1, v: 6])
}

//A stupid composite of all that

fac = composite {
    withSensors([valroseComposite, eurecomTop, eurecomBottom])
    filter({ x -> x != 0 })
    map({ x -> x * x })
    reduce({ res, sensor -> res / sensor })
    frequency 1 / h
}

////////////////////////////////////

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play facParking, ext, longTrip, fac
}