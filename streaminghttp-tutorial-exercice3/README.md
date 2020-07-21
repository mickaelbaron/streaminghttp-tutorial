# Exercice 3 : réaliser la communication pour la mise en attente d'une nouvelle manche

Ce troisième exercice propose d'implémenter la partie cliente de la communication Server-Sent Event pour la mise en attente d'une nouvelle manche. Nous utiliserons l'API cliente JAX-RS avec son module `SseEventSource` ainsi que le langage Java.

La partie serveur de cette communucation est déjà implémentée. Cet exercice se focalise exclusivement au développement de la partie cliente.

## But

* Savoir utiliser le module `SseEventSource` de JAX-RS pour initialiser une communication Server-Sent Event.
* Savoir utiliser **cURL** pour se connecter à une communication Server-Sent Event.

## Étapes à suivre

Avant de développer le code client permettant de réaliser la communication Server-Sent Event, nous allons vérifier si cette communication fonctionne en utilisant l'outil en ligne de commande **cURL**.

* Exécuter le projet serveur _spellwhatroyal-server_ à partir de la configuration d'exécution créée dans l'exercice 2.

* Ouvrir un terminal et exécuter la commande suivante en utilisant **cURL** pour se connecter au Server-Sent Event (ne s'intéresser qu'au message dont la valeur de `state` est `PRE_GAME`).

```console
...
$ curl -H "Accept:text/event-stream" http://localhost:8080/game/timer
event: update-timer
id: 1
retry: 1000
data: {"counter":3,"state":"PRE_GAME"}

event: update-timer
id: 1
retry: 1000
data: {"counter":2,"state":"PRE_GAME"}
...
```

La fréquence de rafraichissement est fixé à 500 ms. Vous remarquerez une structure qui retourne un ensemble de données : `event` un nom, `id` un identifiant, `retry` le temps avant une tentative de reconnexion du client vers le serveur, `data` la charge utile (_payload_) transmis vers le client. C'est cette dernière information qui servira au client pour mettre à jour l'écran de mise en attente d'une nouvelle manche. Nous sommes donc prêts à développer le code client.

* Ouvrir le projet _spellwhatroyal-swingclient_ et commencer par éditer le fichier _pom.xml_ pour ajouter le module `SseEventSource` de JAX-RS afin de prendre en compte les communications Server-Sent Event.

```xml
    <dependencies>
        <dependency>
            <groupId>org.glassfish.jersey.media</groupId>
            <artifactId>jersey-media-sse</artifactId>
        </dependency>
    </dependencies>
```

Nous montrons ci-dessous un exemple de code pour se connecter et traiter une communication Server-Sent Event. Comme l'API cliente est basée sur JAX-RS, l'initialisation est similaire à celle pour l'appel à un service web REST.

```Java
// Création du client.
Client client = ClientBuilder.newClient();

// Création du chemin racine.
WebTarget webTarget = client.target("http://IP:PORT");

// Création de chemins intermédiaires (si nécessaire).
WebTarget book = webTarget.path("/books/queryparameters");

// Création d'un objet SseEventSource.
SseEventSource eventSource = SseEventSource.target(webTarget).build();

// Abonnement à des codes (interfaces fonctionnelles) qui traiteront les nouvelles données, les erreurs et la déconnexion.
eventSource.register(onEvent, onError, onComplete);

Consumer<InboundSseEvent> onEvent = (InboundSseEvent inboundSseEvent) -> {
    // Code qui traitera les données reçues du serveur.
    Book readData = inboundSseEvent.readData(Book.class);
}

Consumer<Throwable> onError = (throwable) -> {
    // Code qui traitera les erreurs.
};

Runnable onComplete = () -> {
    // Code qui traitera la déconnexion du serveur
}

// Ouverture de la connexion
eventSource.open();
```

* Depuis le projet _spellwhatroyal-swingclient_, compléter la méthode `SpellWhatRoyalController#createSseEventSource()` en respectant l'algorithme suivant :
  * créer un objet `WebTarget` en utilisant les mêmes paramètres que dans l'exemple avec **cURL** ;  
  * créer un objet `SseEventSource` à partir de l'objet `WebTarget` précédement construit ;
  * compléter les trois fonctions lambda (`onEvent`, `onError` et `onComplete`) dont le code est décrit ci-dessous :
    * `onEvent` : récupérer la charge utile et selon l'état `state` appeler les méthodes `preGame`, `inGame` ou `postGame` en transmettant l'objet (voir signature des méthodes) ;
    * `onError` : afficher la trace des erreurs ;
    * `onComplete` : appeler la méthode `goToAuthentication()`.
  * ouvrir la connexion.

* Compléter la méthode `SpellWhatRoyalController#preGame(GameData)` afin de mettre à jour les différents éléments de l'écran de mise en attente. La méthode `SpellWhatRoyalController#showWaitingServerUI()` est utilisée pour afficher l'écran de mise en attente.

* Tester le projet client _spellwhatroyal-swingclient_ à partir de la configuration d'exécution créée à la fin de l'exercice 2.
