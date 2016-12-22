# Tech Poker

## Goal

Build a Spring Boot App to play planing poker with your team.

## Prerequesites
* Installed Java (8)
* IntelliJ (Ultimate for Spring Support)

## Getting Started

### Clone this Repository
* Create your own branch with your 'teamname'

### Create your Spring Boot App with the Spring Initialzr
* Either go to https://start.spring.io or use IntelliJ new Spring Boot Project
* Generate a **Maven Project** and set your groupId to 'org.oneandone.tech.poker.\<teamname>' artifactId 'tech-poker-app'
* Select the following dependencies: Web, Thymeleaf, Actuator, Configuration Processor, Session, Validation
* Download and unzip the Project/Finish the IntelliJ wizard

### Import the Project into IntelliJ
* Copy the resources from this checkout into the _src/main/resources/static_ folder 
  and move the _templates_ one level up (_src/main/resources/templates_)
* Delete the application.properties in _src/main/resources_ and create an application.yml


## Poker

* There is one poker session master and n-players
* You become master by creating a new session
* Players can join via the session url and choose a name
* Repeat until session ends
  * The master calls for a vote
  * Each player can now submit his vote (optional, players can change their vote until voted is finalized)
  * The result is shown if either all players submitted their vote, the master closes the vote, or a timeout is reached
  * Result should include
    * Average
    * Min, Max
    * Distribution?

## Design suggestions
* Event Sourcing, CQRS
* Ephemeral Poker Sessions (no Database Persistence, simple in memory store)

## Stretch goals

### Show QR Code to join

Create a QR Code with https://github.com/zxing/zxing/wiki/Getting-Started-Developing and show it on the page. 
(Hint: you can use data-url encoding)

```xml
<dependency>
    <groupId>com.google.zxing</groupId>
    <artifactId>core</artifactId>
    <version>3.3.0</version>
</dependency>
```

You could also use https://davidshimjs.github.io/qrcodejs/ to render it completly on the client side.

### Use Websockets
Use spring-websockets to add updates via Websockets to the app.

* Player vote status is now directly shown via on the master screen
