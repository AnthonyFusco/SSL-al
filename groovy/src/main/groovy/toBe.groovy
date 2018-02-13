mathlaw "polynome1" addligne "0" : "x<0.22"
mathlaw "polynome1" addligne "2*x^2 + 10*x^3" : "x<2.27"
mathlaw "polynome1" addligne "3" : "x > 2.28"
mathlaw "poly" addligne "value" : "cond"

koukou = { int x ->
    return  (x < 5) ?  6 : 4
}



law "poly1" ofType FunctionLaw withExpressions koukou


parkingLaw "parkingLaw"

sensorLot "top" sensorsNumber 5 withLaw "parkingLaw" withFrequency 1 / h

sensorLot "bot" sensorsNumber 15 withLaw "parkingLaw" withFrequency 2 / h

composite "eurecom" withLots (["top", "bot"]) filter({x -> x}) /*map({x -> x}) reduce({res, sensor -> res + sensor}, 0)*/