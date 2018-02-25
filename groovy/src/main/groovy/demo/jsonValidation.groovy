package demo

createOrResetDB()

bike = jsonReplay {
    path "datafiles/bike_bad.json"
    offset 10.s
    noise([100,50])
}

bike2 = jsonReplay {
    path "datafiles/bike_bad2.json"
    offset 10.s
    noise(["100X", 200])
}

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play bike, bike2
}