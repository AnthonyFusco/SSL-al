//todo create and drop influx table from script
//todo add a test for the demo
//todo some validation

resetDB()

//TODO write macros like this one

//#define MARKOV => law ("markovLaw" + i++) ofType MarkovLaw stateFrequency 1 / 20.s

def random = randomLaw {}
random = randomLaw {

}
//law "markovLaw" ofType MarkovLaw stateFrequency 1 / 20.s matrix([[0.3, 0.2, 0.5], [0.15, 0.8, 0.05], [0.25, 0.25, 0.5]])

//law "polynomialLaw" ofType FunctionLaw expression simpleExpression
//dataSource "poly2" ofType FunctionLaw itReturns "STRING" expression (["Shiny": "x>0.22","Rainy" : "x<0.22"])

//sensorLot "markovLot" sensorsNumber 2 law "markovLaw0" frequency 1 / s
//sensorLot "polynomialLot" sensorsNumber 2 law "polynomialLaw" frequency 1 / s
//sensorLot "randomLot" sensorsNumber 2 law "randomLaw" frequency 1 / s

def s1 = sensorLot {
    sensorsNumber 2
    law random
    frequency 1 / min
}

def s2 = sensorLot {
    sensorsNumber 2
    law random
    frequency 2 / min
}
//String filepath = "/home/fpastor/GroovyProjects/SSL-al/groovy/src/main/resources/rawdata/inconsistent.csv"
//replay "file1" path path columns([t: 0, s: 1, v: 8]) offset 10.s
/*replay "file1" with {
    path filepath
    columns([t: 0, s: 1, v: 8])
    offset 10.s
}*/

play s1, s2

runSimulation "10/02/2018 09:25:00", "10/02/2018 09:30:00"