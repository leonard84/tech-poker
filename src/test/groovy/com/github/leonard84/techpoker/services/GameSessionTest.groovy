package com.github.leonard84.techpoker.services

import com.github.leonard84.techpoker.data.Choice
import com.github.leonard84.techpoker.data.GameStats
import com.github.leonard84.techpoker.data.Result
import org.springframework.messaging.simp.SimpMessagingTemplate

import spock.lang.Specification
import spock.lang.Subject

class GameSessionTest extends Specification {

    SimpMessagingTemplate simpMessagingTemplate = Stub()

    @Subject
    GameSession gameSession = new GameSession(simpMessagingTemplate: simpMessagingTemplate)

    def "a player can join"() {
        when:
        def playerId = gameSession.join('player')

        then:
        playerId
    }

    def "a joined player can vote"() {
        given:
        def playerId = gameSession.join('player')

        when:
        gameSession.vote(playerId, Choice.V13)

        then:
        noExceptionThrown()
    }

    def "result is calculated on demand with no players"() {
        when:
        Result result = gameSession.tally()

        then:
        verifyAll(result) {
            average == 0.0d
            max == Integer.MIN_VALUE
            min == Integer.MAX_VALUE
        }
    }

    def "result is calculated on demand with votes"() {
        given:
        def player1 = gameSession.join('player1')
        gameSession.vote(player1, Choice.V13)

        def player2 = gameSession.join('player2')
        gameSession.vote(player2, Choice.V13)

        def player3 = gameSession.join('player3')
        gameSession.vote(player3, Choice.COFFEE)

        when:
        Result result = gameSession.tally()

        then:
        result.average == 13.0d
        result.max == 13
        result.min == 13

        and:
        result.votes.choice == Choice.values() as List

        and:
        result.votes.players == [[], [], [], [], [], ['player1', 'player2'], [], [], [], ['player3'], []]

        and:
        result.votes.count == [0, 0, 0, 0, 0, 2, 0, 0, 0, 1, 0]
    }

    def "game stats can be queried for no players"() {
        when:
        GameStats stats = gameSession.stats

        then:
        stats.currentVotes == 0
        stats.playerVotes.empty
    }


    def "game stats can be queried for players"() {
        given:
        def player1 = gameSession.join('player1')

        when:
        GameStats stats = gameSession.stats

        then:
        stats.currentVotes == 0
        stats.playerVotes.size() == 1
        !stats.playerVotes[0].hasVoted()

        when:
        gameSession.vote(player1, Choice.V13)
        stats = gameSession.stats

        then:
        stats.currentVotes == 1
        stats.playerVotes.size() == 1
        stats.playerVotes[0].hasVoted()
    }

    def "votes can be reset"() {
        given:
        def player1 = gameSession.join('player1')
        gameSession.vote(player1, Choice.V13)

        expect:
        gameSession.stats.currentVotes == 1

        when:
        gameSession.reset()

        then:
        gameSession.stats.currentVotes == 0
    }

    def "a player can change his vote"() {
        given:
        def player1 = gameSession.join('player1')
        gameSession.vote(player1, Choice.V13)

        expect:
        with(gameSession.tally()) {
            min == 13
            max == 13
        }

        when:
        gameSession.vote(player1, Choice.V5)

        then:
        with(gameSession.tally()) {
            min == 5
            max == 5
        }
    }

    def "a players vote can be queried"() {
        given:
        def player1 = gameSession.join('player1')
        gameSession.vote(player1, Choice.V13)

        expect:
        gameSession.getVote(player1) == Choice.V13
    }


    def "a players name can be queried"() {
        given:
        def player1 = gameSession.join('player1')

        expect:
        gameSession.getName(player1) == 'player1'
    }
}
