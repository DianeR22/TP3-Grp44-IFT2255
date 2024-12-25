Application maVille - Gestion de travaux publics et privés
Description brève: MaVille est une application en ligne de commande qui permet aux résidents et aux intervenants d'accéder à diverses fonctionnalités 
telles que la consultation des travaux en cours ou à venir, la soumission de requêtes, le suivi des progrès des travaux, la consultation des entraves routières, etc. 
L'objectif de l'application est de faciliter la communication entre les citoyens et les autorités publiques ou privées, tout en favorisant l'inclusion et la participation
active des résidents dans la gestion des travaux.

Liste des fonctionnalités de l'application par rôle:
Rôle du résident:
- Inscription
- Consulter les travaux en cours ou à venir : Avoir accès aux travaux publics ou privés.
- Rechercher des travaux : Filtrer les travaux par titre, quartier ou par type.
- Recevoir des notifications : Recevoir des notifications par défaut sur les projets du quartier du résident. 
- Participer à la planification : Permettre une planification participative des travaux.
- Soumettre une requête : Le résident peut soumettre des requêtes pour signaler des besoins spécifiques en terme de travaux.
- Suivi de requêtes : Suivi de l'état des requêtes soumises.
- Consulter les entraves routières : Obtenir des informations sur les entraves routière. Possible de les filtrer
  par rue ou par un travail en particulier.
  
Rôle de l'intervenant:
- Inscription
- Consulter les requêtes : Gestion et suivi des requêtes soumises par les résidents.
- Soumission de candidature: soumettre sa candidature à une requête, il peut la supprimer.
- Planifier les travaux : Soumettre des projets de travaux
- Mettre à jour les information d'un projet : Modifier les informations d'un projet

Organisation du répertoire: 
/src : Le code source du projet.
README.md : Fichier de documentation du projet. 
javadoc.zip : documentation javaDoc
jaCoco.zip :  documentation jaCoco pour les tests

Données de l'application:

1. Données des travaux : Les travaux sont identifiés par un ID unique et incluent des informations comme le quartier,
le motif (type de projet), le nom de l'intervenant, les dates de début et de fin, une description du projet, etc. Les données proviennent
d'une API publique sur les travaux à Montréal et peuvent être filtrées par type ou quartier.
2. Les entraves sont des impactes des travaux publics, qui comprennent des informations comme la rue,
le type d'impact, le type d'entrave, les dates de début et de fin, etc. Ces données proviennent d'une API
publique de la ville de Montréal pour fournir des informations adéquates.
3. Les projets : des travaux créés par les utilisateurs. Ils incluent des informations comme que l'ID du projet, le nom, le type,
le statut, les dates de début et de fin, etc.
4. Données utilisateurs : le nom, le type (résident ou intervenant), l'adresse e-mail, le mot de passe,
et l'historique des travaux et entraves consultés, permettant de suivre les actions de l'utilisateur.
Les données de l'application sont stockées dans des fichiers json. Les travaux et entraves ne sont pas stockées étant donné qu'ils
proviennt d'une API. Il y a donc des fichiers pour résidents, intervenants, projets, candidatures, un fichier reason_category qui sert à
extraire les types de travaux de l'API et permet d'associer ces types aux types proposés aux utilisateurs (comme travaux routiers par exemple qui est liée
à Réseaux routier - Réfection et travaux corrélatif).

Pour installer le projet: utiliser la java version la plus récente et faire: Add as a library pour ajouter la librairie json qui se trouve dans le directory libs.
Instruction pour lancer l'application : 
1. cd /chemin/vers/le/dossier/application.jar
2. java -jar application.jar

Pour exécuter les tests dans le terminal:
Assurez-vous d'abord d'avoir installé la version la plus récente de java, c'est-à-dire la 23. Aussi, assurez-vous d'installer Maven (Apache Maven 3.9.9). 

Comment télécharger Maven: 
1. Rdv sur la page officielle de Maven et téléchargez la version binaire (exemple, apache-maven-3.9.9-bin.zip).
2. Ajoutez Maven au path : allez dans Ce PC -> clique droit sur propriétés (properties) -­> Paramètres système avancés (Advanced system settings) ->  Variables d'environnement (Environment Variables) -> Variables système  (System variables) -> trouvez la variable Path et sélectionnez-la -> cliquez sur Modifier (Edit) ->
Cliquez sur Nouveau (New) -> ajoutez le chemin vers le dossier bin de Maven et cliquez sur OK.
3. Vérifiez l'installation avec mvn -v dans le terminal.
4. Vous pouvez maintenant exécuter les tests!
   Commandes: 
   - cd /chemin/vers/le/dossier/ (chemin du dossier du projet)
   - mvn test 
Voilà!
