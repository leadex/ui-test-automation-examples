package kotlinselenide.rules

import com.codeborne.selenide.Configuration.*
import com.codeborne.selenide.WebDriverRunner
import com.codeborne.selenide.logevents.SelenideLogger
import kotlinselenide.allure.StepBuilder
import org.apache.log4j.BasicConfigurator
import org.apache.log4j.Level
import org.apache.log4j.Logger
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.openqa.selenium.support.events.EventFiringWebDriver
import utils.Highlighter
import java.util.concurrent.TimeUnit

object Configuration : TestWatcher() {

    override fun starting(description: Description?) {
        selenideConfiguration()
        log4jConfiguration()
        driverConfiguration()
    }

    private fun selenideConfiguration() {
        browser = "chrome"
        driverManagerEnabled = true
        headless = false
        startMaximized = true
        holdBrowserOpen = false
        savePageSource = false
        screenshots = false
        timeout = 30000

        SelenideLogger.addListener("StepBuilder", StepBuilder)
    }

    private fun log4jConfiguration() {
        BasicConfigurator.configure()
        Logger.getRootLogger().level = Level.OFF
    }

    private fun driverConfiguration() {
        with(EventFiringWebDriver(WebDriverRunner.getWebDriver())) {
            register(Highlighter)
            manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS)
            WebDriverRunner.setWebDriver(this)
        }
    }
}
