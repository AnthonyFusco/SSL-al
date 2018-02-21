package demo

resetDB()

law "Alpha" ofType RandomLaw range([10, 20])
//dataSource "Alpha" ofType RandomLaw range ([10,20])

//randomLaw "Beta"

sensorLot "AlphaLot" law "Alpha" sensorsNumber 1 frequency 1 / s

//sensorLot "BetaLot" sensorsNumber 20 law "Toto"

play "AlphaLot"

runSimulation "10/02/2018 09:25:00", "10/02/2018 09:30:00"