package demo

resetDB()

extfunc = { x ->
    if (x < 7) {
        4
    } else if(x < 12) 6
    else if(x < 14) 9
    else if(x < 16) 9
    else if(x > 16) 8
    else 4
}

extfunc2 = { x ->
    def f = { ->
        f()
    }
    f()
    extfunc2(x)
}

def SensorTempExt2 = mathFunction {
    expression extfunc
}

def lot = sensorLot {
    sensorsNumber 2
    law SensorTempExt2
    frequency 1 / min
}

play lot

runSimulation "10/02/2018 07:25:00", "10/02/2018 18:30:00"