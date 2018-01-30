noise "noise1" [-10,10]

law "file1" fromFile "url://path"
format CSV
withColumns ([t: 1, s: 2, v: 3])
setoffset "file1" 10
setNoise "file1" [-5,5]

law "file42" fromFile "url://path" withOffset 10 withNoise "noise1"

sensorLot "parking" withLaw "file1"

createSensorsSet "set1" sensorsNumber 50 withLaw "lawName" withDuration 1


runSimulation