# Exercice 2 : réaliser la communication pour rejoindre une partie et identifier un joueur

Ce deuxième exercice propose d'implémenter la partie cliente de la communication pour l'identification d'un joueur. Nous utiliserons l'API cliente JAX-RS et le langage Java.

La partie serveur de cette communication est déjà implémentée.

## But

* Développer un code client d'un service web REST.
* Utiliser l'API cliente JAX-RS et le langage Java.

## Étapes à suivre

* Démarrer l'environnement de développement Eclipse.

* Importer le projet Maven **spellwhatroyal** disponible dans le répertoire _workspace_ (**File -> Import -> Maven -> Existing Maven Projects**), choisir le répertoire du projet, puis faire **Finish**.

* Examiner les différents sous-dossiers du projet « Spell What Royal »
  * _spellwhatroyal-server_ projet pour la partie serveur développé en Java ;
  * _spellwhatroyal-swingclient_ projet pour la partie cliente développé en Java et Swing ;
  * _spellwhatroyal-api_ projet commun à tous les projets Java.

Nous allons démarrer le serveur pour tester la future communication.

* Exécuter le projet serveur _spellwhatroyal-server_ à partir d'une nouvelle configuration d'exécution **Java Application** depuis Eclipse (**Run -> Run Configurations... -> New Launch Configuration**). Choisir le projet _spellwhatroyal-server_ et saisir comme classe principale `com.kumuluz.ee.EeApplication`.

* Ouvrir un terminal et exécuter la commande suivante en utilisant **cURL** pour rejoindre une partie de « Spell What Royal » et identifier le joueur _J1_ sur le serveur.

```console
$ curl -H "Content-Type: application/json" -X POST -d '{"username":"J1"}' http://localhost:8080/authentication

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

* Depuis le projet _spellwhatroyal-swingclient_ compléter la méthode `SpellWhatRoyalController#createPlayer(String value)` en respectant l'algorithme suivant :
  * créer un objet `Credentials` à partir du nom saisi sur l'écran de connexion ;
  * invoquer le service web ;
  * si le statut de la réponse est 200 :
    * extraire un object `CredentialsResult` depuis la réponse ;
    * mettre à jour le modèle du jeu avec le nom et le token obtenu ;
    * invoquer la méthode `SpellWhatRoyalController#start()`.
  * sinon :
    * afficher un message d'erreur sur la console.

* Tester le projet client _spellwhatroyal-swingclient_ à partir d'une nouvelle configuration d'exécution **Java Application** depuis Eclipse (**Run -> Run Configurations... -> New Launch Configuration**). Choisir le projet _spellwhatroyal-swingclient_ et saisir comme classe principale `fr.mickaelbaron.spellwhatroyal.swingclient.controller.SpellWhatRoyalController`.
