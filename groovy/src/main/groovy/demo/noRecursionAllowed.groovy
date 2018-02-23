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

def recursionLaw = mathFunction {
    expression recursionFunction
}

def recursionLot = sensorLot {
    sensorsNumber 2
    law recursionLa
    frequency 1 / min
}

play recursionLot

runSimulation "10/02/2018 07:25:00", "10/02/2018 18:30:00"