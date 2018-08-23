package utils

import com.codeborne.selenide.Selenide
import com.codeborne.selenide.WebDriverRunner
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.StaleElementReferenceException
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

interface Markers {

    val paint get() = "arguments[0].setAttribute('style', " +
            "'background: #f8e85b; color: #c3122f');"
    val clean get() = "arguments[0].setAttribute('style','');"

    fun highlightElement(element: WebElement?, driver: WebDriver?) {
        val attrClass = element!!.getAttribute("class")
        if (!attrClass.contains("Widget")) {
            with(driver as JavascriptExecutor) {
                executeScript(Highlighter.paint, element)
                Selenide.sleep(100L)
                executeScript(Highlighter.clean, element)
                Selenide.sleep(100L)
                executeScript(Highlighter.paint, element)
                Selenide.sleep(100L)
                executeScript(Highlighter.clean, element)
                Selenide.sleep(100L)
            }
        }
    }

    fun markElement(e: WebElement) {
        val cl = e.getAttribute("class")
        if (!cl.contains("Widget")) {
            try {
                val js = WebDriverRunner.getWebDriver() as JavascriptExecutor
                js.executeScript("arguments[0].setAttribute('style', 'background: #68bf47');", e)
            } catch (e: StaleElementReferenceException) {
                //
            }
        }
    }
}