package demo

resetDB()

sprint = replay {
    path "/home/afusco/IdeaProjects/SSL-al/groovy/src/main/resources/rawdata/data1.csv"
    offset 1.h
    columns([v : 8])
    noise([0.01, 0.15])
}

longTrip = replay {
    path "/home/afusco/IdeaProjects/SSL-al/groovy/src/main/resources/rawdata/data2.csv"
    columns([t : 0, s : 1, v : 5])
}

play sprint, longTrip

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"