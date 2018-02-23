package demo

resetDB()

alpha = randomLaw {
    range([10, 20])
}

AlphaLot = sensorLot {
    sensorsNumber 5
    law alpha
    frequency 1 / min
}

play AlphaLot

runSimulation "10/02/2018 09:25:00", "10/02/2018 09:30:00"