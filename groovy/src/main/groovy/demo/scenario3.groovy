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

law "temperatureExerieure" ofType FunctionLaw withExpressions extfunc
//State 1 : Comfy  State2: Uncomfortable
law "AtmoshpereInt" ofType MarkovLaw withMatrix ([[0.8, 0.2], [0.6, 0.4]]) changeStateFrequency 1 / min

sensorLot "SensorLumiereExt" withLaw "luminosite" withFrequency 2 / h sensorsNumber 2

sensorLot "SensorTempExt" sensorsNumber 4 withLaw "temperatureExerieure" withFrequency 1/h

sensorLot "SensorAtmoshpereInt" sensorsNumber 4 withLaw "AtmoshpereInt" withFrequency 1/h

runSimulation "VoletAutomatique", "10/02/2018 07:25:00", "10/02/2018 18:30:00"