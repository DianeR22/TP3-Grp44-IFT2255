# Feedback DM1

Disclaimer : Tout ce qui est mentionné dans le feedback sont les points et les détails à améliorer. Si des aspects du devoir n'ont pas été critiqués, c'est qu'ils sont déjà bons.

## Glossaire

- Il manque la définition de "MaVille" ou "Application MaVille" elle-même.
- "Info Entraves et Travaux" est aussi une entité importante à laquelle l'application est reliée.

## Diagramme de CUs

- Un utilisateur général avec lequel le résident et l'intervenant peuvent se généraliser aurait dû être utile pour mettre en commun la connexion et la modification de profil.

## Scénarios

- Lorsqu'un CU (1) est désigné comme un <<include>> à un autre CU (2), alors le CU (1) doit être la première étape du scénario du CU (2), pas une précondition. Donc la connexion à un compte résident/intervenant doit être la première étape de tous les autres scénarios.
- Il faut un noeud de fusion après le choix de se connecter en tant que résident ou en tant qu'intervenant, ou s'inscrire.
- Dans "Faire le suivi d'une requête de travail", "Planification de début de projet" et "Début de projet" sont des étapes invalides, car non seulement c'est l'intervenant qui s'en charge, mais ça sort également du cadre du cas d'utilisation.

## Diagramme d'activités

Bien!

## Analyse

### Risques

- Le risque de performance et de scalabilité est trop similaire au risque de surcharge des serveurs lors des pics de trafic : Dans les deux cas, il s'agit du problème de la fréquence d'activité sur l'application VS sa capacité de traitement. 
- Il faut proposer une solution de mitigation pour chaque risque. Pour certains risques, vous avez donné cette solution implicitement mais on voudrait que ce soit plus explicite, par exemple dans une phrase à part.

### Besoins non-fonctionnels

Bien!

### Besoins matériels

- Les besoins matériels concernent aussi le matériel et l'interface dont l'utilisateur a besoin (un ordinateur avec Java installé en l'occurence). On aurait aussi voulu une potentielle analyse de coûts de vos choix matériels pour le déploiement et le stockage.

### Solution de stockage

Bien!

### Solution d'intégration

Bien!

## Prototype

- Entrer un input quelconque dans le menu principal du résident nous renvoie à la page de connexion/inscription sans au moins afficher un message concernant le menu dans lequel on vient de tenter d'entrer.

## Git

Bien!

## Rapport

Bien!

