package demo

createOrResetDB()

alpha = randomLaw {
    range([10, 20])
}

AlphaLot = sensorLot {
    sensorsNumber 10
    law alpha
    frequency 1 / s
}

play AlphaLot

runSimulation "10/02/2018 09:25:00", "10/02/2018 19:30:00"