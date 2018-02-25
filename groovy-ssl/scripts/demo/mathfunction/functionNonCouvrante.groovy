//special validation case

createOrResetDB()

SensorTempExt = mathFunction {
    expression { t ->
        if (t < 7) 4
        else if (t < 12) 6
        else if (t < 14) 9
        else if (t < 16) 42
        //else 8
    }
}

lab = sensorLot {
    sensorsNumber 2
    law SensorTempExt
    frequency 3 / 40.min
}

simulate {
    start "10/02/2018 07:25:00"
    end "10/02/2018 18:30:00"
    play lab
}