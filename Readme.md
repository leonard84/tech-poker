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
* Select the following dependencies: Web, Thymeleaf, Actuator, Configuration Processor
* Download and unzip the Project/Finish the IntelliJ wizard

### Import the Project into IntelliJ
* Copy the resources from this checkout into the _src/main/resources/static_ folder 
  and move the _templates_ one level up (_src/main/resources/templates_)
* Delete the application.properties in _src/main/resources_ and create an application.yml


## Poker

* There is one poker session master and n-players
* You become master by creating a new session
* Players can join via the session url (QR-Code) and choose a name
* Repeat until session ends
  * The master calls for a vote
  * Each player can now submit his vote (optional, players can change their vote until the vote is finalized)
  * The result is shown if either all players submitted their vote, the master closes the vote, or a timeout is reached
  * Result should include
    * Average
    * Min, Max
    * Distribution?
    * Details (individual votes)

## Non functional Requirements
* Test Driven Development
   * Use Spock and Geb or MockMvc-Test
* Ephemeral Poker Sessions (no Database Persistence, simple in memory store)
* Mobile-first development (responsive)
  * Chrome supports device "emulation" via reduced screen resolution and touch input 


## Stretch goals

### Use Websockets
Use spring-websockets to add updates via Websockets to the app.

* Player vote status is now directly shown via on the master screen

## Documentation
- http://docs.spring.io/spring-boot/docs/current/reference/html/
- https://api.jquery.com/
- http://www.thymeleaf.org/documentation.html
  - http://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html
  - http://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html
- https://getbootstrap.com/css/
- https://getbootstrap.com/components/
- http://spockframework.org/spock/docs/1.1-rc-3/index.html
- http://www.gebish.org/manual/current/
- Add example maven config for Spock
- Example Thymeleaf usage in DMID DMDA https://gerrit.demail.dev.server.lan/#/admin/projects/dmid-dmda
- https://davidshimjs.github.io/qrcodejs/
- Check Deployment to openshift

## Adding spock to maven

Add this profile to your pom and create the `src/test/groovy` directory. The spock test should end with `Test`
so that surefire picks them up.

```xml
<profiles>
    <profile>
        <id>spock</id>
        <activation>
            <file>
                <exists>src/test/groovy</exists>
            </file>
        </activation>
        <build>
            <plugins>
                <!-- Mandatory plugins for using Spock -->
                <plugin>
                    <!-- The gmavenplus plugin is used to compile Groovy code. To learn more about this plugin,
                    visit https://github.com/groovy/GMavenPlus/wiki -->
                    <groupId>org.codehaus.gmavenplus</groupId>
                    <artifactId>gmavenplus-plugin</artifactId>
                    <version>1.4</version>
                    <executions>
                        <execution>
                            <goals>
                                <goal>testCompile</goal>
                            </goals>
                        </execution>
                    </executions>
                </plugin>
            </plugins>
        </build>

        <dependencies>
            <!-- Mandatory dependencies for using Spock -->
            <dependency>
                <groupId>org.spockframework</groupId>
                <artifactId>spock-core</artifactId>>
                <version>1.1-groovy-2.4-rc-3</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.spockframework</groupId>
                <artifactId>spock-spring</artifactId>>
                <version>1.1-groovy-2.4-rc-3</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </profile>
</profiles>
```
