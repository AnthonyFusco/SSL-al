package demo

resetDB()

law "Alpha" ofType RandomLaw withinRange([10,20])
//dataSource "Alpha" ofType RandomLaw withinRange ([10,20])

//randomLaw "Beta"

sensorLot "AlphaLot" withLaw "Alpha" sensorsNumber 1 withFrequency 1 / s

//sensorLot "BetaLot" sensorsNumber 20 withLaw "Toto"

play "AlphaLot"

runSimulation "10/02/2018 09:25:00", "10/02/2018 09:30:00"