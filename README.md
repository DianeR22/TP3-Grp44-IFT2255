Application maVille - Gestion de travaux publics et privés

Description brève: MaVille est une application en ligne de commande qui permet à des résidents et à des intervenants  de la ville de Montréal d'accéder à diverses fonctionnalités 
telles que la consultation des travaux en cours ou à venir, la soumission de requêtes (demande ou besoins spécifiques de travaux des résidents), la consultation des entraves routières, soumission de candidatures pour un travail de la part des intervenants, etc. 
L'objectif de l'application est de faciliter la communication entre les citoyens et les autorités publiques ou privées, tout en favorisant l'inclusion et la participation active des résidents. En effet, l'application permte également aux résidents de spécifier des préférences horaires (jour et heure) pour des travaux dans leur quartier. MaVille est donc une application qui permet aux résidents d'être des acteurs clés de la vie communautaire et de transformer Montréal en une ville connectée et collaborative!

Liste des fonctionnalités de l'application par rôle:
Rôle du résident:
- Inscription et connexion du résident
- Préférence de plages horaires: les résidents peuvent préciser des préférences de plages horaires dans leur quartier.
- Consulter les travaux en cours ou à venir : Avoir accès aux travaux et aux projets soumis par les intervenants.
- Rechercher des travaux : Filtrer les travaux par titre, quartier ou par type de travail.
- Recevoir des notifications : Recevoir des notifications lorsque des projets sont soumis par des intervenants ou qu'un statut de projet est modifié.
- Soumettre une requête : Le résident peut soumettre des requêtes pour signaler des besoins spécifiques en terme de travaux.
- Suivi de requêtes : Suivi de l'état des requêtes soumises, voir si un intervenant a soumis une candidature.
- Consulter les entraves routières : Obtenir des informations sur les entraves routière. Possible de les filtrer
  par rue ou par un travail en particulier.
  
Rôle de l'intervenant:
- Inscription et connexion de l'intervenant
- Consulter les requêtes : Gestion et suivi des requêtes soumises par les résidents
- Soumission de candidature: soumettre sa candidature à une requête, possibilité de la supprimer.
- Planifier les travaux : Soumettre des projets de travaux
- Mettre à jour le statut d'un projet 

Organisation du répertoire: 
/data: contient les fichiers json tels que : requetes.json, residents.json, intervenants.json, candidatures.json, preferences.json, projets.json et reason_category.json (contient les types de travail de l'API des travaux pour être associés aux types de travail proposés de l'application)
/diagrammes : contient tous les diagrammes d'activité, de séquences, de classes
/libs : contient une librairie json
/src : Le code source du projet.
README.md : Fichier de documentation du projet. 
Rapport.html
javadoc.zip : documentation javaDoc
jaCoco.zip :  documentation jaCoco pour les tests

Données de l'application:

1. Données des travaux : Les travaux sont identifiés par un ID unique et incluent des informations comme le quartier,
le motif (type de projet), le nom de l'intervenant, les dates de début et de fin, une description du projet, etc. Les données proviennent d'une API publique sur les travaux à Montréal et peuvent être filtrées par type ou quartier.
2. Les entraves sont les conséquences des travaux publics. Elles comprennent des informations comme la rue,
le type d'impact, le type d'entrave, les dates de début et de fin, etc. Ces données proviennent d'une API
publique de la ville de Montréal pour fournir des informations adéquates.
3. Les projets : Propositions de travaux de la part des intervenants. Ils incluent des informations comme 
le titre, la description, le type de travaux, le quartier, les dates de début et de fin, etc.
4. Données utilisateurs : le nom et prénom, l'adresse e-mail, le mot de passe, etc. Pour les résidents, cela inclut l'adresse, le numéro de téléphone (facultatif),date de naissance, etc. Pour l'intervenant, cela inclut l'identifiant de la ville, son type (public, privé, individuel), etc.
Les données de l'application sont stockées dans des fichiers json. Les travaux et entraves ne sont pas stockées étant donné qu'ils proviennt de deux API. Il y a donc des fichiers pour les résidents, les intervenants, les requêtes, les projets, les candidatures, les préférences ainsi qu'un fichier reason_category qui sert à
extraire les types de travaux de l'API et permet d'associer ces types aux types proposés aux utilisateurs (comme travaux routiers par exemple qui est liée à Réseaux routier - Réfection et travaux corrélatif).

Pour installer le projet: Vous devez utiliser la version la plus récente de java (23).
Instruction pour lancer l'application : java -jar /chemin/vers/le/dossier/application.jar

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
