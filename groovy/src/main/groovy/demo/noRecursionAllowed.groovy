package demo

//Only for error handling example

createOrResetDB()

recursionFunction = { t ->
    def f = { ->
        f()
    }
    f()
    recursionFunction(t)
}

recursionLaw = mathFunction {
    expression recursionFunction
}

recursionLot = sensorLot {
    sensorsNumber 2
    law recursionLaw
    frequency 1 / min
}

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play recursionLot
}