package kotlinselenide.rules

import com.codeborne.selenide.WebDriverProvider
import com.codeborne.selenide.WebDriverRunner
import org.junit.AssumptionViolatedException
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.logging.LogType
import org.openqa.selenium.logging.LoggingPreferences
import org.openqa.selenium.remote.CapabilityType
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.remote.LocalFileDetector
import org.openqa.selenium.remote.RemoteWebDriver
import utils.Attachment
import utils.Constants.LOCAL_HOST
import java.net.MalformedURLException
import java.net.URI
import java.util.*
import java.util.logging.Level

object Selenoid : WebDriverProvider {

    private var inContainers = false

    private val capabilities = DesiredCapabilities()

    override fun createDriver(c: DesiredCapabilities): WebDriver {

        videoUUID = UUID.randomUUID().toString()

        with(capabilities) {
            browserName = "chrome"
            setCapability(CapabilityType.LOGGING_PREFS, loggingPreferences())
            setCapability("enableVNC", inContainers)
            setCapability("enableVideo", inContainers)
            setCapability("videoName", "video_$videoUUID.mp4")
        }

        try {
            val url = URI.create("$LOCAL_HOST/wd/hub").toURL()
            val driver = RemoteWebDriver(url, capabilities)
            driver.manage().window().size = Dimension(1280, 1024)
            driver.fileDetector = LocalFileDetector()
            WebDriverRunner.setWebDriver(driver)
            return driver
        } catch (e: MalformedURLException) {
            throw RuntimeException(e)
        }
    }

    private fun loggingPreferences(): LoggingPreferences {
        with(LoggingPreferences()) {
            enable(LogType.BROWSER, Level.ALL)
            enable(LogType.CLIENT, Level.ALL)
            enable(LogType.DRIVER, Level.ALL)
            return this
        }
    }

    private var videoUUID: String? = null

    object Allure : TestWatcher(), Attachment {

        override fun starting(description: Description?) {
            capabilities.setCapability("name", description!!.displayName)
        }

        override fun skipped(e: AssumptionViolatedException?, description: Description?) {
            if (inContainers) attachVideoHtml(videoUUID!!, LOCAL_HOST)
        }

        override fun failed(e: Throwable?, description: Description?) {
            if (inContainers) attachVideoHtml(videoUUID!!, LOCAL_HOST)
        }
    }
}