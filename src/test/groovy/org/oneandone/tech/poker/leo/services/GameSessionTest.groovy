package org.oneandone.tech.poker.leo.services

import org.oneandone.tech.poker.leo.data.Choice
import org.oneandone.tech.poker.leo.data.GameId
import org.oneandone.tech.poker.leo.data.GameStats
import org.oneandone.tech.poker.leo.data.Result

import spock.lang.Specification
import spock.lang.Subject

class GameSessionTest extends Specification {

    @Subject
    GameSession gameSession = new GameSession(new GameId())

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
        result.average == 0.0
        result.max == 0
        result.min == 0
    }

    def "result is calculated on demand with votes"() {
        given:
        def player1 = gameSession.join('player1')
        gameSession.vote(player1, Choice.V13)

        when:
        Result result = gameSession.tally()

        then:
        result.average == 13.0
        result.max == 13
        result.min == 13
    }

    def "game stats can be queried for no players"() {
        when:
        GameStats stats = gameSession.getStats()

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

        when:
        gameSession.vote(player1, Choice.V13)
        stats = gameSession.stats

        then:
        stats.currentVotes == 1
        stats.playerVotes.size() == 1
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
