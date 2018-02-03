
law "polynome1" ofType "MathFunction" itReturns "INT"
mathlaw "polynome1" addligne "0" : "x<0.22"
mathlaw "polynome1" addligne "2*x^2 + 10*x^3" : "x<2.27"
mathlaw "polynome1" addligne "3" : "x > 2.28"
mathlaw "poly" addligne "value" : "cond"

law "markov1" ofType "MarkovChain" withMatrix ([0.9,0.075,0.025][0.15,0.8,0.05][0.25,0.25,0.5])
