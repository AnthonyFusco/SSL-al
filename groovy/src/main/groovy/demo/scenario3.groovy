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
    else if(x < 12) 6
    else if(x < 14) 9
    else if(x < 16) 9
    else if(x > 16) 8
    else 4
}

extfunc2 = {x ->
     def f = { ->
        f()
    }
    f()
    extfunc2(x)
}

//law "temperatureExerieure" ofType FunctionLaw withExpressions extfunc

def l = law "temperatureExerieure2" ofType FunctionLaw withExpressions extfunc2
//State 1 : Comfy  State2: Uncomfortable
//law "AtmoshpereInt" ofType MarkovLaw withMatrix ([[0.8, 0.2], [0.6, 0.4]]) changeStateFrequency 1 / min

//sensorLot "SensorLumiereExt" withLaw "luminosite" withFrequency 2 / h sensorsNumber 2

//sensorLot "SensorTempExt" sensorsNumber 1 law "temperatureExerieure" frequency 1/h
sensorLot "SensorTempExt2" sensorsNumber 1 law l frequency 1/h

//sensorLot "SensorAtmoshpereInt" sensorsNumber 4 withLaw "AtmoshpereInt" withFrequency 1/h

play "SensorTempExt2"

runSimulation "10/02/2018 07:25:00", "10/02/2018 18:30:00"