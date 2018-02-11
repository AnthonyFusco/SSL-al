//todo create and drop influx table from script
//todo add a test for the demo
//todo some validation

resetDB()

randomLaw "randomLaw" //useless, could be inlined

law "markovLaw" ofType MarkovLaw withMatrix([[0.3, 0.2, 0.5], [0.15, 0.8, 0.05], [0.25, 0.25, 0.5]]) changeStateFrequency 1 / 20.s

simpleExpression = { x ->
    return (x < 5) ? 2 * (x**2) : 4
}

law "polynomialLaw" ofType FunctionLaw withExpressions simpleExpression
//law "poly2" ofType FunctionLaw itReturns "STRING" withExpressions (["Shiny": "x>0.22","Rainy" : "x<0.22"])

sensorLot "markovLot" sensorsNumber 2 withLaw "markovLaw" withFrequency 1 / s
//sensorLot "polynomialLot" sensorsNumber 2 withLaw "polynomialLaw" withFrequency 1 / s
//sensorLot "randomLot" sensorsNumber 2 withLaw "randomLaw" withFrequency 1 / s

String path = "/home/afusco/IdeaProjects/SSL-al/groovy/src/main/resources/rawdata/data1.csv"
//replay "file1" fromPath path withColumns([t: 0, s: 1, v: 8]) withOffset 10.s

runSimulation "fac", "10/02/2018 09:25:00", "10/02/2018 09:30:00"