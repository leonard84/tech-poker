package org.oneandone.tech.poker.leo.services

import spock.lang.Specification
import spock.lang.Subject

class GameServiceTest extends Specification {

    @Subject
    GameService gameService = new GameService()

    def "a new game can be created and subsequently queried"(){
        when:
        def gameId = gameService.createNewGame()

        then:
        gameService.getById(gameId)
    }
}
