package demo

createOrResetDB()

alpha = randomLaw {
    range([10, 20])
}

alphaLot = sensorLot {
    sensorsNumber 10
    law alpha
    frequency 1 / s
}

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play alphaLot
}