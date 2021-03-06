//simple mathematical law

createOrResetDB()

SensorTempExt = mathFunction {
    expression { t ->
        if (t < 7) 4
        else if (t < 12) 6
        else if (t < 14) 9
        else if (t < 16) 42
        else 8
    }
}

func2 = { t -> t * 2 + 1 }

SensorLab = mathFunction {
    expression func2
    noise([-5, 5.9])
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