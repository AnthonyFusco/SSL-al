# SSL Groupe G

## Lois implémenté :

### Loi Aléatoire

Exemple :

Une loi aléatoire se déclare avec le mot clef randomLaw.

La loi prend en paramètre :
 - Un intervalle sous le mot clef range
    - Cet intervalle est utilisé pour générer les valeurs aléatoires.

```groovy
randomLaw {
     range([10, 20])
 }
```

### Chaine de Markov

Une chaine de Markov se déclare avec le mot clef markovChain.

La loi prend en paramètre :
- Une matrice sous le mot clef matrix
  - Représente la chaine de Markov
- Une fréquence sous le mot clef stateFrequency
  - Correspond à la fréquence à laquelle la loi va calculer un changement d'état

Exemple :

```groovy
markovChain {
    matrix([[0.7, 0.3], [0.3, 0.7]])
    stateFrequency 2 / h
}
```

### Fonction Mathématique

La loi prend en paramètre :
- Une Closure Groovy sous le mot clef expression
    - représente l'expression mathematique à utiliser

Exemples :

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


## Replay de fichier implémenté :

### Replay de CSV


La loi prend en paramètres :
- Un chemin sous le mot clef path
  - Indique quel fichier contient les données à rejouer
- Une map sous le mot clef columns
  - Indique quelle colonne utiliser pour :
    - t : la colonne représentant le temps
    - s : la colonne représentant le nom du sensor
    - v : la colonne représentant la valeur
- Une durée sous le mot clef offset
  - Représente une durée à ajouter à chaque valeur de la colonne t
- Une range sous le mot clef noise
  - Représente un intervalle de valeur dans lequel sera pris une valeur aléatoire qui sera ajoutée à chaque valeur du fichier 

Exemples :

```groovy
csvReplay {
    path "datafiles/data1.csv"
    columns([t: 0, s: 1, v: 6])
    offset 1.h
    noise([0.01, 0.15])
}
```

### Replay de Json

La loi prend en paramètres :
- Un chemin vers le fichier JSON sous le mot clef path
- Une durée sous le mot clef offset
    - Définissant l'offset sur les dates    
- Une range sous le mot clef
    - Définissant l'intervale de valeurs que peut prendre le bruit appliqué aux valeurs du fichier  
- Une String sous le mot clef recordToken
    - Définissant le token utilisé dans le fichier JSON pour récuperer le tableau des enregistrements
- Une String sous le mot clef timeToken
    - Définissant le token utilisé dans le fichier JSON pour récuperer le champ temps dans le tableau des enregistrements
- Une String sous le mot clef valueToken
    - Définissant le token utilisé dans le fichier JSON pour récuperer le champ valeur dans le tableau des enregistrements
- Une String sous le mo clef nameToken 
    - Définissant le token utilisé dans le fichier JSON pour récupérer le nom du senseur
    
Exemple :
  
```groovy
bike2 = jsonReplay {
    path "datafiles/bike2.json"
    offset 10.s
    noise([100, 200])
    recordToken "r"
    valueToken "val"
    timeToken "time"
    nameToken "nn"
}
```

  

## Utilisation :


Une fois qu'une loi a été déclarée et configurée, il faut déclarer un lot de sensors qui utilisera cette loi.

Pour cela, on utilise le mot clef sensorLot, avec en paramètres :
- Un nombre de sensor sous le mot clef sensorsNumber
- La loi à utiliser sous le mot clef law
- La frequence d'envoi des données vers la BD sous le mot clef frequency

Une fois le lot de sensor configuré, on déclare une simulation avec le mot clef simulate et en paramètres :
- La date d'origine de la simulation sous le mot clef start
  - Sous forme dd/MM/yyyy HH:mm:ss
- La date de fin de la simulation sous le mot clef end
  - Sous forme dd/MM/yyyy HH:mm:ss
- Les lot de sensors, replay ou composite à exécuter sous le mot clef play

Exemple :

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


## Extension Sensors Composite :


Un sensor composite aggrege les valeurs des sensors qui lui sont passées en paramètre et retourne une valeur.

Les valeurs retournées par ces sensors peuvent être transformées avec les fonctions filter/map/reduce.

Dans l'exemple d'au dessus, on utilise deux lot de sensors représentant des parkings en paramètre d'un composite pour 
calculer l'occupation d'un parking.

Un composite se déclare avec le mot clef composite et prend en paramètres :
- Une liste de sensors sous le mot clef withSensors
  - Peut être des sensorsLot, Replay ou Composite
- Une Closure sous le mot clef filter
  - Fonction identité par defaut si non spécifié
- Une Closure sous le mot clef map
  - Fonction identité par defaut si non spécifié
- Une Closure sous le mot clef reduce
- La frequence d'envoi des données vers la BD sous le mot clef frequency

Exemples :

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
  
## Execution :

