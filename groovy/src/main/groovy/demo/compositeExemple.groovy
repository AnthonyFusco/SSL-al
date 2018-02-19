package demo

resetDB()

parkingLaw "parkingLaw"
sensorLot "top" sensorsNumber 2 law "parkingLaw" frequency 1 / h
sensorLot "bot" sensorsNumber 2 law "parkingLaw" frequency 2 / h
//composite "eurecom" withLots (["top", "bot"]) filter({x -> x == x}) map({x -> x}) reduce({res, sensor -> res + sensor}) frequency 2 / h
parkingComposite "test" withLots (["top", "bot"])

//randomLaw "rand"
//sensorLot "randy" sensorsNumber 2 law "rand" frequency 1 / d
//sensorLot "rando" sensorsNumber 2 law "rand" frequency 1 / d
//composite "randrand" withLots (["randy", "rando"]) filter({x -> x != 0}) map({x -> 2 / x}) reduce({res, sensor -> res + sensor}) frequency 1 / min

play "test"

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"