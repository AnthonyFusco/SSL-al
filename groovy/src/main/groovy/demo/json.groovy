package demo

resetDB()

def bike = jsonreplay {
    path "/home/afusco/IdeaProjects/SSL-al/groovy/src/main/resources/rawdata/bike.json"
    offset 10.s
    noise ([100,200])
}

play bike

runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"