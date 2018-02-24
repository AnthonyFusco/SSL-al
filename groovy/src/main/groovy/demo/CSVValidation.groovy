package demo

createOrResetDB("influxdb")

sprint = csvReplay {
    path "datafiles/notATime.csv"
    offset 1.h
    columns([v: 8])
    noise([0.01, 0.15])
}

longTrip = csvReplay {
    path "datafiles/inconsistent.csv"
    columns([t: 0, s: 1, v: 5])
}

play sprint, longTrip
runSimulation "10/02/2018 08:00:00", "10/02/2018 19:00:00"