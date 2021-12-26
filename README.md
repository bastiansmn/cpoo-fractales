# cpoo-fractales
Projet de CPOO 2021 | Création d'un générateur graphique de fractales

# Lancer le projet :

Pour build le projet en un Jar executable, vous devez utilieser le Makefile. 
La commande `make` se chargera de le créer.
Vous pouvez ensuite lancer cette executable avec la commande :
```
java -jar fractal.jar arg1 arg2 ...
```
Par défaut, le programme sans argument lancera l'interface graphique, tandis qu'on peut aussi lancer le projet ainsi :
```
java -jar fractal.jar -c=-0.8+i0.156 
```
Ceci va générer une fractale avec un bref descriptif dans le shell.

Voici une liste des options comlète.

# Choix techniques du projet :



# Fonctionnalités :

- Choisir f(z), c, zoom, ...
- Créer des ensembles connus (Mandelbrot, Julia, )
- Créer des classes qui lancent les ensemble avec les parametres donnés
- Choisir le code couleur (couleur hors bornes/dans l'ensemble)