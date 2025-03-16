# Exercice 2 : réaliser la communication pour rejoindre une partie et identifier un joueur

Ce deuxième exercice propose d'implémenter la partie cliente de la communication pour l'identification d'un joueur. Nous utiliserons l'API cliente JAX-RS et le langage Java.

La partie serveur de cette communication est déjà implémentée.

## But

- Développer un code client d'un service web REST.
- Utiliser l'API cliente JAX-RS et le langage Java.

## Étapes à suivre

- Démarrer l'éditeur [VSCode](https://code.visualstudio.com/ "Visual Studio Code").

- Ouvrir le dossier du projet Maven **spellwhatroyal** disponible dans le répertoire _workspace_.

- Examiner les différents sous-dossiers du projet « Spell What Royal »
  - _spellwhatroyal-server_ projet pour la partie serveur développé en Java ;
  - _spellwhatroyal-swingclient_ projet pour la partie cliente développé en Java et Swing ;
  - _spellwhatroyal-api_ projet commun à tous les projets Java.

Nous allons démarrer le serveur pour tester la future communication en démarrant un serveur d'applications Java depuis le projet Maven **spellwhatroyal-server** (sous projet de **spellwhatroyal**).

- Comme le projet Maven **spellwhatroyal-server** a une dépendance vers le projet Maven **spellwhatroyal-api**, construire et installer dans le dépôt local Maven ce projet API. Se placer dans le dossier _spellwhatroyal-api_ et exécuter la commande Maven suivante :

```bash
cd spellwhatroyal-api
mvn clean install
```

La sortie console attendue :

```bash
...
[INFO] --- jar:3.4.1:jar (default-jar) @ spellwhatroyal-api ---
[INFO] Building jar: /Users/baronm/workspacepersowebserviceslabs/streaminghttp-tutorial-solution/workspace/spellwhatroyal/spellwhatroyal-api/target/spellwhatroyal-api-0.1-SNAPSHOT.jar
[INFO]
[INFO] --- install:3.1.2:install (default-install) @ spellwhatroyal-api ---
[INFO] Installing /Users/baronm/workspacepersowebserviceslabs/streaminghttp-tutorial-solution/workspace/spellwhatroyal/spellwhatroyal-api/pom.xml to /Users/baronm/.m2/repository/fr/mickaelbaron/spellwhatroyal-api/0.1-SNAPSHOT/spellwhatroyal-api-0.1-SNAPSHOT.pom
[INFO] Installing /Users/baronm/workspacepersowebserviceslabs/streaminghttp-tutorial-solution/workspace/spellwhatroyal/spellwhatroyal-api/target/spellwhatroyal-api-0.1-SNAPSHOT.jar to /Users/baronm/.m2/repository/fr/mickaelbaron/spellwhatroyal-api/0.1-SNAPSHOT/spellwhatroyal-api-0.1-SNAPSHOT.jar
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  1.964 s
[INFO] Finished at: 2025-03-16T11:55:52+01:00
[INFO] ------------------------------------------------------------------------
```

- Exécuter le projet serveur d'applications Java _spellwhatroyal-server_ à partir de Maven. Se placer dans le dossier _spellwhatroyal-server_ et exécuter la commande Maven suivante :

```bash
cd spellwhatroyal-server
mvn clean liberty:run
```

La sortie console attendue :

```bash
Toutes les fonctions ont été vérifiées.
[INFO] Installing features: [jsonb, jsonp, restfulws, websocket, cdi]
[INFO] Product validation completed successfully.
[INFO] The following features have been installed: websocket cdi-4.0 jsonb jsonb-3.0 servlet-6.0 cdi jndi-1.0 jsonp-2.1 jsonp restfulWSClient-3.1 restfulWS restfulWS-3.1 websocket-2.1
[INFO] Running liberty:deploy
[INFO] Copying 1 file to /Users/baronm/workspacepersowebserviceslabs/streaminghttp-tutorial-solution/workspace/spellwhatroyal/spellwhatroyal-server/target/liberty/wlp/usr/servers/defaultServer
[INFO] CWWKM2144I: Mettez à jour le fichier de configuration du serveur server.xml à partir de /Users/baronm/workspacepersowebserviceslabs/streaminghttp-tutorial-solution/workspace/spellwhatroyal/spellwhatroyal-server/src/main/liberty/config/server.xml.
[INFO] CWWKM2185I: The liberty-maven-plugin configuration parameter "appsDirectory" value defaults to "apps".
[INFO] Application configuration is found in server.xml : spellwhatroyal-server.war
[INFO] CWWKM2160I: Installation de l'application spellwhatroyal-server.war.xml.
[INFO] Copying 1 file to /Users/baronm/workspacepersowebserviceslabs/streaminghttp-tutorial-solution/workspace/spellwhatroyal/spellwhatroyal-server/target/liberty/wlp/usr/servers/defaultServer
[INFO] CWWKM2144I: Mettez à jour le fichier de configuration du serveur server.xml à partir de /Users/baronm/workspacepersowebserviceslabs/streaminghttp-tutorial-solution/workspace/spellwhatroyal/spellwhatroyal-server/src/main/liberty/config/server.xml.
[INFO] CWWKM2185I: The liberty-maven-plugin configuration parameter "appsDirectory" value defaults to "apps".
[INFO] CWWKM2001I: Invoke command est [/Users/baronm/workspacepersowebserviceslabs/streaminghttp-tutorial-solution/workspace/spellwhatroyal/spellwhatroyal-server/target/liberty/wlp/bin/server, run, defaultServer].
[INFO]
[INFO] Lancement de defaultServer (Open Liberty 25.0.0.2/wlp-1.0.98.cl250220250209-1902) sur OpenJDK 64-Bit Server VM, version 11.0.2+9 (fr_FR)
[INFO] [AUDIT   ] CWWKE0001I: Le serveur defaultServer a été lancé.
[INFO] [AUDIT   ] CWWKZ0058I: Recherche d'applications dans dropins.
[INFO] [AUDIT   ] CWWKT0016I: Application Web disponible, (default_host) : http://localhost:9080/
[INFO] Starting game.
[INFO] [AUDIT   ] CWWKZ0001I: Application spellwhatroyal-server démarrée en 1,607 secondes.
[INFO] ConfigExecution [spellwhatroyal.init.delay=0, spellwhatroyal.pregame.delay=3, spellwhatroyal.ingame.delay=10, spellwhatroyal.postgame.delay=4]
[INFO] [AUDIT   ] CWWKF0012I: Le serveur a installé les fonctions suivantes : [cdi-4.0, jndi-1.0, jsonb-3.0, jsonp-2.1, restfulWS-3.1, restfulWSClient-3.1, servlet-6.0, websocket-2.1].
[INFO] [AUDIT   ] CWWKF0011I: Le serveur defaultServer est prêt pour une planète plus intelligente. Il a démarré en 3,681 secondes.
```

- Ouvrir un terminal et exécuter la commande suivante en utilisant **cURL** pour rejoindre une partie de « Spell What Royal » et identifier le joueur _J1_ sur le serveur.

```console
curl -H "Content-Type: application/json" -X POST -d '{"username":"J1"}' http://localhost:9080/authentication
```

La sortie console attendue :

```bash
{"token":"1"}
```

Comme vous pouvez le constater, l'appel au service web REST retourne un token qui sera utilisé pour identifier le joueur _J1_ pendant une partie. Il vous est proposé de réaliser la même opération en utilisant l'API cliente JAX-RS. Pour rappel, voici comment utiliser cette API client d'invocation à un service web.

```Java
// Création du client.
Client client = ClientBuilder.newClient();

// Création du chemin racine.
WebTarget webTarget = client.target("http://IP:PORT");

// Création de chemins intermédiaires (si nécessaire).
WebTarget book = webTarget.path("/books/queryparameters");

// Création d'une requête.
Builder request = book.request();

// Construire un objet Entity en lui donnant l'instance de l'objet à envoyer et le format d'échange.
Entity<Book> myBookEntity = Entity.entity(myBook, MediaType.APPLICATION_JSON_TYPE);

// Invoquer la requête en choisissant une méthode HTTP. Dans le cas ci-dessous, c'est une méthode POST qui est utilisée.
Response post = request.post(myBookEntity);

// L'objet Response permet de connaître le status de la réponse et de récupérer un objet en retour.
post.getStatus(); // Code de la réponse

BookResult readEntity = post.readEntity(BookResult.class); // Extraction de l'objet de la réponse.
```

> Vous pouvez utiliser cette API cliente en une seule ligne !!!

```Java
Response response = ClientBuilder.newClient().target("http://127.0.0.1:9992").path("/books/queryparameters").request().post(Entity.entity(myBook, MediaType.APPLICATION_JSON_TYPE));
```

- Depuis le projet _spellwhatroyal-swingclient_ compléter la méthode `SpellWhatRoyalController#createPlayer(String value)` en respectant l'algorithme suivant :

  - créer un objet `Credentials` à partir du nom saisi sur l'écran de connexion ;
  - invoquer le service web ;
  - si le statut de la réponse est 200 :
    - extraire un objet `CredentialsResult` depuis la réponse ;
    - mettre à jour le modèle du jeu avec le nom et le token obtenu ;
    - invoquer la méthode `SpellWhatRoyalController#start()`.
  - sinon :
    - afficher un message d'erreur sur la console.

- Depuis le projet _spellwhatroyal-swingclient_, tester votre code en exécutant la classe principale `fr.mickaelbaron.spellwhatroyal.swingclient.controller.SpellWhatRoyalController`.
