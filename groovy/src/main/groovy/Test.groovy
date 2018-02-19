//todo create and drop influx table from script
//todo add a test for the demo
//todo some validation

//resetDB()

randomLaw "randomLaw" //useless, could be inlined

law "markovLaw" ofType MarkovLaw withMatrix([[0.3, 0.2, 0.5], [0.15, 0.8, 0.05], [0.25, 0.25, 0.5]]) changeStateFrequency 1 / 20.s

simpleExpression = { x ->
    return (x < 5) ? 2 * (x**2) : 4
}

law "polynomialLaw" ofType FunctionLaw withExpressions simpleExpression
//dataSource "poly2" ofType FunctionLaw itReturns "STRING" withExpressions (["Shiny": "x>0.22","Rainy" : "x<0.22"])

//sensorLot "markovLot" sensorsNumber 2 law "markovLaw" frequency 1 / s
//sensorLot "polynomialLot" sensorsNumber 2 law "polynomialLaw" frequency 1 / s
sensorLot "randomLot" sensorsNumber 2 law "randomLaw" frequency 1 / s

sensorLot "s2" with {
    sensorsNumber 10
    law "randomLaw"
    frequency 20 / s
}

String filepath = "/home/fpastor/GroovyProjects/SSL-al/groovy/src/main/resources/rawdata/inconsistent.csv"
//replay "file1" path path columns([t: 0, s: 1, v: 8]) offset 10.s
replay "file1" with {
    path filepath
    columns([t: 0, s: 1, v: 8])
    offset 10.s
}
runSimulation "10/02/2018 09:25:00", "10/02/2018 09:30:00"