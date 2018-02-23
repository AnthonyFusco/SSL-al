package demo

//Only for error handling example

resetDB()

recursionFunction = { t ->
    def f = { ->
        f()
    }
    f()
    recursion(t)
}

recursionLaw = mathFunction {
    expression recursionFunction
}

recursionLot = sensorLot {
    sensorsNumber 2
    law recursionLaw
    frequency 1 / min
}

play recursionLot

runSimulation "10/02/2018 07:25:00", "10/02/2018 18:30:00"