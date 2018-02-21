package demo

resetDB()



//dataSource "parkingLaw" ofType MarkovLaw withMatrix([[0.3, 0.7], [0.2, 0.8]]) changeStateFrequency 2 / h

//parkingLaw "parkingLaw"

def l = law "parkingLaw" ofType MarkovLaw withMatrix([[0.5,0.5], [0.4, 0.6]]) changeStateFrequency 1 / h
sensorLot "eurecom" sensorsNumber 5 law l frequency 10 / h

koukou = {
    x = 5
    for (i = 0; i > -1; i++) {
        System.out.println("Hello World")
    }
}
koukou()
//String path = "/home/afusco/IdeaProjects/SSL-al/groovy/src/main/resources/rawdata/data1.csv"
//data1.csv
//notATime.csv

//replay "Velo" path path columns([t: 0, s: 1, v: 8]) offset 10.s noise ([100, 200])

play "eurecom"

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"