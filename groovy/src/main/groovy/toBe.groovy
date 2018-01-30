law "random1" random

law "markov1" markovChain
state1 to state2 with 0.5
state2 to state1 with 0.2

noise "noise1" [-10,10]

law "file1" fromFile "url://path"
format CSV
where t 1 s 2 v 3
setoffset "file1" 10
setNoise "file1" [-5,5]

law "file42" fromFile "url://path" withOffset 10 withNoise "noise1"

sensorLot "parking" withLaw "file1"

createSensorsSet "set1" sensorsNumber 50 withLaw "lawName" withDuration 1


runSimulation