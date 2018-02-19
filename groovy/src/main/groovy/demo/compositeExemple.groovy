package demo

resetDB()

parkingLaw "parkingLaw"
sensorLot "top" sensorsNumber 2 withLaw "parkingLaw" withFrequency 1 / h
sensorLot "bot" sensorsNumber 2 withLaw "parkingLaw" withFrequency 2 / h
//composite "eurecom" withLots (["top", "bot"]) filter({x -> x == x}) map({x -> x}) reduce({res, sensor -> res + sensor}) withFrequency 2 / h
parkingComposite "test" withLots (["top", "bot"])

//randomLaw "rand"
//sensorLot "randy" sensorsNumber 2 withLaw "rand" withFrequency 1 / d
//sensorLot "rando" sensorsNumber 2 withLaw "rand" withFrequency 1 / d
//composite "randrand" withLots (["randy", "rando"]) filter({x -> x != 0}) map({x -> 2 / x}) reduce({res, sensor -> res + sensor}) withFrequency 1 / min

play "test"

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"