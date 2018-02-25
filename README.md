# SSL Groupe G

## Lois implémenté :

###Loi Aléatoire

Example :

Une loi aléatoire se déclare avec le mot clef randomLaw.

La loi prend en paramètre :
 - une range, cette range est utilisé pour générer les valeurs aléatoires.

```groovy
randomLaw {
     range([10, 20])
 }
```

###Chaine de Markov

Une chaine de Markov se déclare avec le mot clef markovChain.

La loi prend en paramètre :
- une matrice sous le mot clef matrix
  - représente la chaine de Markov
- Une fréquence sous le mot clef stateFrequency
  - Correspond à la fréquence à laquel la loi va calculer un changement d'état

Example :

```groovy
markovChain {
    matrix([[0.7, 0.3], [0.3, 0.7]])
    stateFrequency 2 / h
}
```

###Fonction Mathématique

Examples :

```groovy
mathFunction {
    expression { t ->
        if (t < 7) 4
        else if (t < 12) 6
        else if (t < 14) 9
        else if (t < 16) 42
        else 8
    }
}

f = { t -> t * 2 + 1 }
mathFunction {
    expression f
}
```

La loi prend en paramètre :
- Une Closure Groovy sous le mot clef expression
    - représente l'expression mathematique à utiliser

## Replay de fichier implémenté :

###Replay de CSV

Examples :

```groovy
csvReplay {
    path "datafiles/data1.csv"
    columns([t: 0, s: 1, v: 6])
    offset 1.h
    noise([0.01, 0.15])
}
```

La loi prend en paramètre :
- Un chemin sous le mot clef path
  - indique quel fichier contient les données à rejouer
- Une map sous le mot clef columns
  - indique quel colonne utiliser pour :
    - t : la colonne représentant le temps
    - s : la colonne représentant le nom du sensor
    - v : la colonne représentant la valeur
- Une durée sous le mot clef offset
  - représente une durée à ajouter à chaque valeur de la colonne t
- Une range sous le mot clef noise
  - représente une range de valeur dans laquel sera prise une valeur aléatoire qui sera ajouté à chaque valeur du fichier 

###Replay de Json

TODO JEREMY

## Utilisation :

Example :

```groovy
exampleLaw = randomLaw {
    range([10, 20])
}
   
exampleSensors = sensorLot {
    sensorsNumber 10
    law exampleLaw
    frequency 1 / s
}

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play exampleSensors
}
```

Une fois qu'une loi a été déclaré et configuré, il faut déclarer un lot de sensors qui utilisera cette loi.

Pour cela, on utilise le mot clef sensorLot, avec en paramètre :
- Un nombre de sensor sous le mot clef sensorsNumber
- la loi à utiliser sous le mot clef law
- la frequence d'envoi des données vers la BD sous le mot clef frequency

Une fois le lot de sensor configuré, on déclare une simulation avec le mot clef simulate et en paramètres :
- la date d'origine de la simulation sous le mot clef start
  - sous forme dd/MM/yyyy HH:mm:ss
- la date de fin de la simulation sous le mot clef end
  - sous forme dd/MM/yyyy HH:mm:ss
- les lot de sensors, replay ou composite à exécuter sous le mot clef play

## Extension Sensors Composite :

Example :

```groovy
//a simple simulation of a parking
eurecomTop = sensorLot {
    sensorsNumber 3
    law markovChain {
        matrix([[0.6, 0.4], [0.1, 0.9]])
        stateFrequency 2 / h
    }
}

//a simple simulation of a parking
eurecomBottom = sensorLot {
    sensorsNumber 7
    law markovChain {
        matrix([[0.8, 0.2], [0.3, 0.7]])
        stateFrequency 1 / h
    }
}

//a composite using two parkings (eurecomTop, eurecomBottom)
eurecom = composite {
    withSensors([eurecomTop, eurecomBottom])
    filter({ x -> x == x })
    map({ x -> x })
    reduce({ res, sensor -> res + sensor })
    frequency 2 / h
}

simulate {
    start "10/02/2018 08:00:00"
    end "10/02/2018 19:00:00"
    play eurecom
}
```

Un sensor composite aggrege les valeurs des sensors qui lui passé en paramètre et retourne une valeur.

Le valeurs retourné par ces sensors peuvent être transformé avec les fonctions filter/map/reduce.

Dans l'exemple d'au dessus, on utilise deux lot de sensors représentant des parkings en paramètre d'un composite pour 
calculer l'occupation d'un parking.

Un composite se déclare avec le mot clef composite et prend en paramètre :
- Une liste de sensors sous le mot clef withSensors
  - peut être des sensorsLot, Replay ou Composite
- Une Closure sous le mot clef filter
  - Fonction identité par defaut si non spécifié
- Une Closure sous le mot clef map
  - Fonction identité par defaut si non spécifié
- Une Closure sous le mot clef reduce
- la frequence d'envoi des données vers la BD sous le mot clef frequency
  
## Execution :

