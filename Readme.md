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
* No internal components should be used, since this might be deployed to an external cloud. 


## Stretch goals

### Cloud Deployment
https://docs.spring.io/spring-boot/docs/current/reference/html/cloud-deployment.html

### Use Websockets
Use spring-websockets to add updates via Websockets to the app.

* Player vote status is now directly shown via on the master screen

## Documentation
- Spring Boot http://docs.spring.io/spring-boot/docs/current/reference/html/
- Thymeleaf http://www.thymeleaf.org/documentation.html (warning spring 1.4.3 uses version 2.1.5 however the documenation is missing)
  - http://www.thymeleaf.org/doc/tutorials/3.0/usingthymeleaf.html
  - http://www.thymeleaf.org/doc/tutorials/3.0/thymeleafspring.html
- JQuery https://api.jquery.com/
- Bootstrap
  - https://getbootstrap.com/css/
  - https://getbootstrap.com/components/
- Spock http://spockframework.org/spock/docs/1.1-rc-3/index.html
- Geb http://www.gebish.org/manual/current/
- Example Thymeleaf usage in DMID DMDA: https://gerrit.demail.dev.server.lan/#/admin/projects/dmid-dmda
- QrCodeJs https://davidshimjs.github.io/qrcodejs/


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
                <artifactId>spock-core</artifactId>
                <version>1.1-groovy-2.4-rc-3</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.spockframework</groupId>
                <artifactId>spock-spring</artifactId>
                <version>1.1-groovy-2.4-rc-3</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <!-- To enable mocking of classes -->
                <groupId>cglib</groupId>
                <artifactId>cglib-nodep</artifactId>
                <version>3.2.4</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </profile>
</profiles>
```


## Adding Geb to Maven

Your Geb Tests shoud end in `IT` so that they are executed with failsafe instead of surefire.
The config uses Chrome, if you can't use it you have to edit it to use another browser.

```xml
<profiles>
    <profile>
        <id>geb</id>
        <activation>
            <file>
                <exists>src/test/groovy</exists>
            </file>
        </activation>
        <build>
            <plugins>
                <!-- Mandatory plugins for using Spock -->
                <plugin>
                    <groupId>com.github.webdriverextensions</groupId>
                    <artifactId>webdriverextensions-maven-plugin</artifactId>
                    <version>3.1.1</version>
                    <executions>
                        <execution>
                            <phase>pre-integration-test</phase>
                            <goals>
                                <goal>install-drivers</goal>
                            </goals>
                        </execution>
                    </executions>
                    <configuration>
                        <keepDownloadedWebdrivers>true</keepDownloadedWebdrivers>
                       
                        <drivers>
                            <driver>
                                <name>chromedriver</name>
                                <version>2.27</version>
                            </driver>
                        </drivers>
                    </configuration>
                </plugin>
                <plugin>
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-failsafe-plugin</artifactId>
                    <configuration>
                        <systemPropertyVariables>
                            <geb.build.reportsDir>target/test-reports/geb</geb.build.reportsDir>
                        </systemPropertyVariables>
                    </configuration>
                </plugin>
            </plugins>
        </build>
        <dependencies>
            <!-- Mandatory dependencies for using Spock -->
            <dependency>
                <groupId>org.gebish</groupId>
                <artifactId>geb-core</artifactId>
                <version>1.1.1</version>
                <scope>test</scope>
            </dependency>
            <dependency>
                <groupId>org.gebish</groupId>
                <artifactId>geb-spock</artifactId>
                <version>1.1.1</version>
                <scope>test</scope>
            </dependency>

            <dependency>
                <groupId>org.seleniumhq.selenium</groupId>
                <artifactId>selenium-chrome-driver</artifactId>
                <version>3.0.1</version>
                <scope>test</scope>
            </dependency>
        </dependencies>
    </profile>
</profiles>
```

Put this in `src/test/resources/GebConfig.groovy`

```groovy
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeDriverService
import org.openqa.selenium.os.CommandLine

File findDriverExecutable() {
    def defaultExecutable = CommandLine.find("chromedriver")
    if (defaultExecutable) {
        new File(defaultExecutable)
    } else {
        new File("drivers").listFiles().find { !it.name.endsWith(".version") }
    }
}

driver = {
    ChromeDriverService.Builder serviceBuilder = new ChromeDriverService.Builder()
        .usingAnyFreePort()
        .usingDriverExecutable(findDriverExecutable())
    new ChromeDriver(serviceBuilder.build())
}

baseUrl = "http://localhost"
```

Minimal example Geb Spec

```groovy
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TechPokerAppApplicationIT extends GebReportingSpec {

    @LocalServerPort
    Integer port

    def 'index loads correctly'() {
        when:
        go "http://localhost:$port"

        then:
        title == 'Tech Poker'
    }
}
```
