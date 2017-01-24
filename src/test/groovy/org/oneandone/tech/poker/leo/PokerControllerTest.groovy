package org.oneandone.tech.poker.leo

import static org.hamcrest.CoreMatchers.containsString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

import javax.inject.Inject

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc

import spock.lang.Specification

@AutoConfigureMockMvc
@SpringBootTest
class PokerControllerTest extends Specification {

    @Inject MockMvc mockMvc

    def "renders index"() {
        expect:
        mockMvc.perform(get('/')).andExpect(view().name('index'))
    }

    def "starts new session index"() {
        expect:
        mockMvc.perform(post('/new')).andExpect(redirectedUrlPattern('/game/*'))
    }


    def "qr url is correctly inserted"() {
        expect:
        mockMvc.perform(get('/game/sessionId'))
                .andExpect(view().name('poker'))
                .andExpect(content().string(containsString('data-qr-url="http://localhost/game/sessionId"')))
    }

    def "join asks for a name"() {
        expect:
        mockMvc.perform(get('/join/sessionId')).andExpect(view().name('join'))
    }

    def "join accepts a name"() {
        expect:
        mockMvc.perform(post('/join/sessionId')).andExpect(redirectedUrlPattern('/vote/*/*'))
    }
}
