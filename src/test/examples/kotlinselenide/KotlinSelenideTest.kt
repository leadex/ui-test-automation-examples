package kotlinselenide

import com.automation.remarks.video.annotations.Video
import com.codeborne.selenide.Condition.visible
import com.codeborne.selenide.Selenide.open
import com.codeborne.selenide.Selenide.sleep
import io.qameta.allure.junit4.DisplayName
import kotlinselenide.data.Elements
import kotlinselenide.rules.Configuration
import kotlinselenide.rules.Selenoid
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.openqa.selenium.Keys
import utils.Allure

class KotlinSelenideTest : TestActions {

    @Rule
    @JvmField
    var configuration = Configuration

    @Rule
    @JvmField
    var selenoidAllure = Selenoid.Allure

    @Rule
    @JvmField
    var allure = Allure

    @Test
    @Video
    @DisplayName("kotlin selenide")
    fun test() {
        with(Elements) {
            searchBox set "Golden Ratio" send Keys.ENTER
            "1.61803" shouldBe visible
            firstResult.."mathematics"
            "Main page".click()
            "Wikipedia" shouldBe visible
            logo shouldBe visible
            Assert.fail()
            sleep(3000)
        }
    }

    @Before
    fun before() {
        open("https://duckduckgo.com/")
    }
}