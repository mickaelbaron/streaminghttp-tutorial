# Exercice 4 : réaliser la communication pour l'écran du jeu

Ce quatrième exercice propose d'implémenter la partie cliente des communications (Server-Sent Event et WebSocket) pour la phase du jeu d'identification de l'image. Nous utiliserons l'API cliente JAX-RS avec son module `SseEventSource` pour la mise à jour continue des informations du jeu (compteur, URI et aide) et l'API Tyrus pour la soumission des propositions d'identification. Les développements se feront avec le langage Java.

La partie serveur de ces communucations est déjà implémentée. Cet exercice se focalise exclusivement au développement de la partie cliente.

## But

* Savoir utiliser **cURL** pour se connecter à une communication Server-Sent Event.
* Savoir utiliser l'API Tyrus pour développer un client WebSocket.
* Comprendre l'utilité des encodeurs et décodeurs.

## Étapes à suivre

* S'assurer que le projet serveur _spellwhatroyal-server_ est toujours en cours d'exécution (voir configuration d'exécution créée dans l'exercice 2).

* Ouvrir un terminal et exécuter la commande suivante en utilisant **cURL** pour se connecter au Server-Sent Event (s'intéresser cette fois au message dont la valeur de `state` est `IN_GAME`).

```console
$ curl -H "Accept:text/event-stream" http://localhost:8080/game/timer
...
event: update-timer
id: 1
retry: 1000
data: {"counter":9,"help":"L'île aux enfants","state":"IN_GAME","uri":"http://idata.over-blog.com/0/00/74/35/33/casimir.jpg"}

event: update-timer
id: 1
retry: 1000
data: {"counter":8,"help":"L'île aux enfants","state":"IN_GAME","uri":"http://idata.over-blog.com/0/00/74/35/33/casimir.jpg"}
...
```

* Depuis le projet _spellwhatroyal-swingclient_, compléter le code de la méthode `SpellWhatRoyalController#inGame(GameData)` afin de mettre à jour les différents éléments de l'écran de jeu (compteur, URI de l'image et aide à l'identification). La méthode `SpellWhatRoyalController#showGameUI()` est utilisée pour afficher l'écran de jeu.

* Tester le projet client _spellwhatroyal-swingclient_ à partir de la configuration d'exécution créée à la fin de l'exercice 2 et s'assurer que les informations sont affichées.

Nous allons maintenant nous intéresser aux communications qui utilisent WebSocket. Différentes interactions sont à développer :

* le client vers le serveur pour transmettre la valeur soumise par le joueur ;
* le serveur vers l'instance du client pour préciser si la valeur soumise est correcte ;
* le serveur vers l'ensemble des instances des clients pour informer du nombre de joueur ayant trouvé la bonne réponse.

* Ouvrir le projet _spellwhatroyal-swingclient_ et compléter le fichier _pom.xml_ pour ajouter la bibliothèque Tyrus afin de prendre en compte les communications WebSocket.

```xml
    <dependency>
        <groupId>org.glassfish.tyrus</groupId>
        <artifactId>tyrus-client</artifactId>
        <version>${tyrus.version}</version>
    </dependency>

    <dependency>
        <groupId>org.glassfish.tyrus</groupId>
        <artifactId>tyrus-container-grizzly-client</artifactId>
        <version>${tyrus.version}</version>
    </dependency>
```

### Étapes à suivre pour la création de la connexion WebSocket

* Depuis le projet _spellwhatroyal-swingclient_, compléter le code de la méthode `SpellWhatRoyalController#createWebsocket()` en recopiant le code ci-dessous.

```java
private void createWebsocket() {
    // Create WS connection.
    final ClientEndpointConfig cec = ClientEndpointConfig.Builder.create()
        .encoders(Arrays.<Class<? extends Encoder>>asList(PlayerDataEncoderDecoder.class))
        .decoders(Arrays.<Class<? extends Decoder>>asList(DataResultDecoder.class)).build();
    try {
        URI uriBuild = UriBuilder.fromUri("ws://" + HOST + "/game/" + this.refModel.getToken()).port(PORT).build();

        currentWSSession = clientWS.connectToServer(new Endpoint() {
            @Override
            public void onOpen(Session session, EndpointConfig config) {
                session.addMessageHandler(new MessageHandler.Whole<DataResult>() {
                    // Sera complété plus tard.
                });
            }
        }, cec, uriBuild);
    } catch (DeploymentException | IOException e) {
        e.printStackTrace();
        throw new NotYetImplementException();
    }
}
```

* Visualiser le code des classes `PlayerDataEncoderDecoder` et `DataResultDecoder` du projet _spellwhatroyal-api_. Ces classes permettent de transmettre des messages à travers des objets.

### Étapes à suivre pour réaliser la communication entre le client et le serveur

La communication WebSocket entre le client et le serveur est utilisée pour transmettre la proposition du joueur.

* Depuis le projet _spellwhatroyal-swingclient_, compléter le code de la méthode `SpellWhatRoyalController#newValue(String)` qui sera appelée à chaque modification de texte.

```java
public void newValue(String value) {
    try {
        PlayerData newPlayerData = new PlayerData();
        newPlayerData.setToken(refModel.getToken());
        newPlayerData.setValue(value);
        currentWSSession.getBasicRemote().sendObject(newPlayerData);
    } catch (IOException e) {
        e.printStackTrace();

        throw new NotYetImplementException();
    } catch (EncodeException e) {
        e.printStackTrace();
        throw new NotYetImplementException();
    }
}
```

### Étapes à suivre pour réaliser la communication entre le serveur et un client

La communication WebSocket entre le serveur et le client est utilisée pour préciser si la valeur soumise par un joueur est correcte.

* Depuis le projet _spellwhatroyal-swingclient_, compléter le code de la méthode `SpellWhatRoyalController#createWebsocket()`.

```java
public void onMessage(DataResult message) {
    if (message instanceof PlayerDataResult) {
        PlayerDataResult refDataResult = (PlayerDataResult) message;
        refGameUI.setCheck(refDataResult.getValid() ? "Yes" : "No");
    }
}
```

À chaque réception d'un contenu du serveur, la méthode `onMessage` sera invoquée. L'objet retourné est de type `DataResult`. Il faudra donc vérifier le sous-type pour extraire les bonnes informations.

* Tester le projet client _spellwhatroyal-swingclient_ et s'assurer que les informations sont affichées.

### Étapes à suivre pour réaliser la communication entre le serveur et tous les clients

La communication WebSocket entre le serveur et l'ensemble des clients est utilisée pour informer le nombre de joueur ayant trouvé la bonne réponse.

* Depuis le projet _spellwhatroyal-swingclient_, compléter le code de la méthode `SpellWhatRoyalController#createWebsocket()`.

```java
public void onMessage(DataResult message) {
    if (message instanceof PlayerDataResult) {
        PlayerDataResult refDataResult = (PlayerDataResult) message;
        refGameUI.setCheck(refDataResult.getValid() ? "Yes" : "No");
    } else if (message instanceof AllPlayerDataResult) {
        AllPlayerDataResult refDataResult = (AllPlayerDataResult) message;
        refGameUI.setOthers(refDataResult.getRightAnswers() + " / " + refDataResult.getPlayers());
    }
}
```

* Afin de vérifier que le serveur communique avec tous les clients, nous allons créer une connexion WebSocket depuis **cURL**.

```console
$ curl --include \
     --no-buffer \
     --header "Connection: Upgrade" \
     --header "Upgrade: websocket" \
     --header "Host: localhost:8080" \
     --header "Origin: http://localhost:8080" \
     --header "Sec-WebSocket-Key: mykey==FAKE_KEY" \
     --header "Sec-WebSocket-Version: 13" \
     http://localhost:8080/game/debug
HTTP/1.1 101 Switching Protocols
Date: Mon, 02 Mar 2020 17:08:15 GMT
X-Powered-By: KumuluzEE/3.6.0
Connection: Upgrade
Sec-WebSocket-Accept: 9xa7+DdrD/15WTOmiAJxTtSkOqU=
Server: Jetty(9.4.15.v20190215)
Upgrade: WebSocket
{"players":1,"rightAnswers":0}
{"players":1,"rightAnswers":0}
{"players":1,"rightAnswers":0}
{"players":1,"rightAnswers":0}
{"players":1,"rightAnswers":0}
{"players":1,"rightAnswers":0}
{"players":1,"rightAnswers":0}{"players":1,"rightAnswers":1}
```

* Tester le projet client _spellwhatroyal-swingclient_ et s'assurer que les informations sont affichées sur l'écran et sur la console (**cURL**).
