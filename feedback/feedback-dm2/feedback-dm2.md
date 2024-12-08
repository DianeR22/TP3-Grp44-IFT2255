# Feedback DM2

## Révision

- Dans le diagramme de CUs, pourquoi avoir relié l'acteur externe MaVille à l'inscription d'un résident? Acteur qui n'est
ensuite pas inclus parmi les acteurs dans le scénario?
- "MaVille" n'est pas un nom d'acteur suffisamment représentatif de son vrai rôle, et on peut facilement le confondre
avec le nom de notre application. "API de la ville" par exemple aurait été meilleur.
- Vous n'avez pas inclus le nouveau CU "Consulter les entraves".

## Architecture 

- Vous perdez malheureusement 3 points car on attendait un diagramme mais pas une description purement textuelle.
- L'architecture est bien décrite cependant.

## Diagramme de classes

- Il n'y a aucune multiplicité mentionnée dans le diagramme.
- La granularité des classes View est partie d'une bonne idée mais aurait pu être mieux centralisée. Là, le contrôleur d'une vue
a exactement la même fonction d'affichage que la vue elle-même, ce qui est une duplication des responsabilités, et viole le
principe de responsabilité unique car le contrôleur ne fait que coordonner les interactions (notamment appeler lui-même la fonction d'affichage de la vue
au lieu d'en avoir une lui-même).

## Diagramme de séquence

- Le but des flèches avec un trait plein est de faire un appel de fonction concret, pas une vague description du comportement provoqué par la flèche.
"Transmettre le filtre par type", quelle fonction est appelée? `appliquerFiltre(filtre)`? Il n'y a que dans les flèches retour en pointillés qu'il est
autorisé de montrer des descriptions textuelles.
- On ne distingue aucune ligne de vie. Les lignes en bas de chaque classe utilisée est totalement uniforme.

## Justification choix de design

Bien!

## Implémentation

- Il semble qu'aucun des comptes donnés dans le rapport ne marchent. En consultant le code, je vois que ça doit
lire des fichiers `intervenants/residents.json` pour vérifier la connexion, sauf qu'ils ne sont pas là quand j'ai uniquement
accès au .jar.
- Apparemment il y a une erreur lors de la sauvegarde de la requête. Le fichier `requetes.json` n'est pas créé.
- D'ailleurs, les fichiers `intervenants.json` et `residents.json` non plus ne sont pas créés et enregistrés la première fois
qu'on ouvre l'application sans que ces fichiers n'existent. Donc après m'être inscrit en tant que résident, quand j'éteins
et rallume l'application, le compte que j'ai créé n'a pas été enregistré. 
- Donc en gros la connexion marche, afficher la liste des travaux, des requêtes et des entraves marchent, mais soumettre une requête de travail 
ne marche pas.

## Tests unitaires

- J'ai une.. eum... `MockitoException` quand j'essaye de lancer les tests. `Mockito cannot mock this class`.
- Vous avez BEAUCOUP de tests hahaha, la plupart sont très pertinents et bien nommés donc vous aurez les points dessus
mais je ne peux malheureusement pas donner les points sur leur exécution. Vous aurez seulement les points pour l'exécution de `RequeteTest`.

## Rapport et Git

Bien!
