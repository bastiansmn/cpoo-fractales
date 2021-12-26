# cpoo-fractales
Projet de CPOO 2021 | Création d'un générateur graphique de fractales

# Lancer le projet :

Pour build le projet en un Jar executable, vous devez utiliser le Makefile. 
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

Voici une liste des options complète des options :
```
Usage :
    -c | --complex : "-c=0.1-0.4i"                              (required)
        Give a complex number for the fractal generation
        If you choose Mandelbrot, give a random one.
    -s | --size : "-s=500"                                 (default s=500)
        The size (in pixel) of the image.
    -f | --framesize : "-f=2.5"                            (default f=2.5)
        The zoom level, the higher this number is, the smaller will be the fractal
    -b | --minBrightness : "-b=0.3"                        (default b=0.3)
        The minimal brightness out of the fractal (use to add contrast to it)
    -r | --colorRange : "-r="[0;360]""                 (default r=[0;360])
        The color range for the fractal
    -p | --properties : "-p="path/file.properties"          (default p="")
        Specify a .properties file with this option.
    -t | --type : "-t=mandelbrot"                        (default t=julia)
        Specify a fractal type (Julia or Mandelbrot at the moment)
    -v | --veroffset : "-v=0.5"                              (default v=0)
        The vertical offset of the rendered fractal
    -h | --horoffset : "-h=-0.1"                             (default h=0)
        The horizontal offset
    -o | --open : "-o=true                               (default o=false)
        If we need to open the fractal after rendreing it
    
```

# Choix techniques du projet :

## 1) La modélisation des fractales
Nous avons mis en commun dans une classe FractalGenerator tous les attributs communs aux fractales (zoom, taille en pixel, luminosité, etc) et dans des classes dédiées (JuliaSet, MandelbrotSet) qui étendent FracalGenerator, nous y plaçons les valeurs particulières comme la fonction polynomiale utilisée (function d'arité 1 pour Julia, et d'arité 2 pour Mandelbrot), ...
Ces classes utilisent des patrons monteurs pour instancier les différents attributs, qui sont requis ou non.

## 2) Utilisation des Threads
Nous avons implémenté 2 façons légèrement différentes d'utiliser les Threads. Ces 2 façons s'appuient sur des ForksJoinPool. La première consiste à générer l'image par sous carrés (un carré par thread) et l'autre de générer l'image par groupe de lignes (un groupe par thread).
Voici une petite étude de l'impact des threads :
```
- Version avec Threads :
Les threads rendent des sous carrés de taille (p) différentes :
1) Pour une image de taille 500x500 avec :
  - p=100 (sous carrés de taille 100x100) : 227ms | 154ms | 169ms | 153ms | 166ms | 151ms
  - p=10 : 828ms | 627ms | 486ms | 559ms ... 
  - p=250 : 100ms | 109ms | 105ms | 110ms 
  - p=300 : 101ms | ...
Donc plus p est petit, moins c'est bien, et p à l'air d'être idéal pour p=size/2

2) Pour 1000x1000 avec :
  - p=100 : 294ms | 308ms | 275ms | 310ms | 295ms
  - p=10 : 1174ms | 1781ms | ...
  - p=250 : 265ms | 246ms | 271ms | 298ms | 289ms | ...
  - p=500 : 236ms | 277ms | 227ms | 255ms | 267ms
  - p=750 : 280ms | 293ms | ...
p=250 et p=500 se valent, on a l'impression qu'il faudrait viser une division de l'image en 4.

3) Pour 2000x2000 avec :
  - p=1000 : 677ms | 742ms | ...
  - p=500 : 693ms | 584ms | 697ms
Et pour p<250 on sent une grosse baisse de performance.

On va prendre p=size/2, car dans tous les cas c'était le plus performant.

- Version sans threads :
1) Pour 500x500 : 127ms | 123ms | 124ms | 132ms
2) Pour 1000x1000 : 334ms | 353ms | 362ms | 334ms
3) Pour 2000x2000 : 1133ms | 1148ms | 1144ms 

Il est clair que la version avec Thread diminue grandement le temps passé, notamment pour les images de résolution élevée
```
Note : Ces calculs ont été réalisés sur un ordinateur portable AMD Ryzen7 4700U **sans être branché**. Il est important de le préciser car les performances multicœurs sont largement supérieures avec un PC sous secteur.

## 3) Autres choix 
De plus, pour apporter plus de flexibilité à la CLI, nous hésitions à utiliser `commons-cli` (donc d'ajouter Maven) ou de créer notre propre gestionnaire d'arguments en ligne de commande. Ainsi, la classe CLIArgsParser gère les arguments sans être obligé de les placer dans un ordre spécifique, et on peut préciser simplement qu'une valeur est required, ...