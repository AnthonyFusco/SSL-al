//all mathematical law validation

createOrResetDB()

SensorTempExt = mathFunction {
    expression { t ->
        if (t < 7) 4
        else if (t < 12) 6
        else if (t < 14) 9
        else if (t < 16) 42
        else 8
        mathFunction(t)
    }
}

func2 = { -> t * t }

SensorLab = mathFunction {
    expression func2
}

ext = sensorLot {
    sensorsNumber 2
    law SensorTempExt
    frequency 3 / h
}

lab = sensorLot {
    sensorsNumber 2
    law SensorLab
    frequency 3 / 40.min
}

simulate {
    start "10/02/2018 07:25:00"
    end "10/02/2018 18:30:00"
    play lab, ext
}