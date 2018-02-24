package demo

createOrResetDB()

bike = jsonReplay {
    path "datafiles/bike.json"
    offset 10.s
    noise([100, 200])
}

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play bike
}