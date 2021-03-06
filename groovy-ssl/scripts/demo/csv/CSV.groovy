//simple csv demonstration

createOrResetDB()

sprint = csvReplay {
    path "datafiles/data1.csv"
    offset 1.h
    columns([v: 8])
    noise([0.01, 0.15])
}

longTrip = csvReplay {
    path "datafiles/data2.csv"
    columns([t: 0, s: 1, v: 6])
}

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play sprint, longTrip
}