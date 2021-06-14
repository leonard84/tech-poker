import org.openqa.selenium.firefox.FirefoxDriver
import org.openqa.selenium.firefox.FirefoxOptions
import org.openqa.selenium.os.ExecutableFinder

File findDriverExecutable() {
    def defaultExecutable = new ExecutableFinder().find("geckodriver")
    if (defaultExecutable) {
        new File(defaultExecutable)
    } else {
        new File("drivers").listFiles().find { !it.name.endsWith(".version") }
    }
}

driver = {
    FirefoxOptions firefoxOptions = new FirefoxOptions()
    System.setProperty('webdriver.gecko.driver', findDriverExecutable().absolutePath)
    FirefoxDriver webDriver = new FirefoxDriver(firefoxOptions)
    webDriver
}

baseUrl = "http://localhost"
