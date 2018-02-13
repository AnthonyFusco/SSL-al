package demo

resetDB()

law "Alpha" ofType RandomLaw

randomLaw "Beta"

sensorLot "AlphaLot" withLaw "Alpha" withFrequency 1 / s

//sensorLot "BetaLot" sensorsNumber 20 withLaw "Toto"

runSimulation "Athene", "10/02/2018 09:25:00", "10/02/2018 09:30:00"