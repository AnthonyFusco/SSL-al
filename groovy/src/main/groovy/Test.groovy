//todo create and drop influx table from script
//todo add a test for the demo
//todo some validation

resetDB()

//TODO write macros like this one

//#define MARKOV => law ("markovLaw" + i++) ofType MarkovLaw stateFrequency 1 / 20.s

def random = randomLaw {}

def markov = markovLaw {
    matrix([[0.5,0.5], [0.7,0.4]])
}

def s1 = sensorLot {
    sensorsNumber 2
    law random
    frequency 1 / min
}

def s2 = sensorLot {
    sensorsNumber 2
    law markov
    frequency 2 / min
}
//String filepath = "/home/fpastor/GroovyProjects/SSL-al/groovy/src/main/resources/rawdata/inconsistent.csv"
//replay "file1" path path columns([t: 0, s: 1, v: 8]) offset 10.s
/*replay "file1" with {
    path filepath
    columns([t: 0, s: 1, v: 8])
    offset 10.s
}*/

//play sensorLot { sensorsNumber 2 law random frequency 1 / min }, sensorLot { sensorsNumber 2 law random frequency 2 / min }

play s1, s2

runSimulation "10/02/2018 09:25:00", "10/02/2018 09:30:00"