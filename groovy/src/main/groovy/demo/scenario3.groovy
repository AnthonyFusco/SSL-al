package demo

/*
Volet Automatique Demo :
-> But : Observer les différents capteurs que va prendre en compte
un volet automatique. Dans le but de lui insufler un comportement intelligent
en fonction de la température de la pièce,la température exterieure
et la luminosité.
 */

resetDB()

//0 si un gros nuage passe devant, 10 Plein soleil
randomLaw "luminosite"

extfunc = {x ->
    if(x < 7)  4
    if(x < 12) 6
    if(x < 14) 9
    if(x < 16) 9
}

intfunc = { x ->
    if(x < 8) 12
    if(x < 16) 18
}

law "temperatureExerieure" ofType FunctionLaw withExpressions extfunc

law "temperatureInterieure" ofType FunctionLaw withExpressions intfunc

sensorLot "SensorLumiereExt" withLaw "luminosite" withFrequency 2 / h sensorsNumber 2

sensorLot "SensorTempExt" sensorsNumber 4 withLaw "temperatureExerieure"

sensorLot "SensorTempInt" sensorsNumber 4 withLaw "temperatureInterieure"

runSimulation "VoletAutomatique", "10/02/2018 07:25:00", "10/02/2018 18:30:00"