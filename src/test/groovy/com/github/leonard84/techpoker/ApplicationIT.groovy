package com.github.leonard84.techpoker

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort

import geb.spock.GebReportingSpec

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApplicationIT extends GebReportingSpec {

    @LocalServerPort
    Integer port

    def 'index loads correctly'() {
        when:
        go "http://localhost:$port"

        then:
        title == 'Tech Poker'
    }
}
