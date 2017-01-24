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
