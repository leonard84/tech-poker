package com.github.leonard84.techpoker.services


import javax.inject.Provider

import com.github.leonard84.techpoker.config.PokerProperties

import spock.lang.Specification
import spock.lang.Subject

class GameServiceTest extends Specification {

    Provider<GameSession> gameSessionProvider = Stub()

    PokerProperties pokerProperties = new PokerProperties()

    @Subject
    GameService gameService = new GameService(gameSessionProvider: gameSessionProvider, pokerProperties: pokerProperties)

    def "a new game can be created and subsequently queried"(){
        given:
        gameSessionProvider.get() >> new GameSession()

        when:
        def gameId = gameService.createNewGame()

        then:
        gameService.getById(gameId)
    }
}
