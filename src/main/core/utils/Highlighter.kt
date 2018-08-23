package utils

import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.support.events.AbstractWebDriverEventListener

object Highlighter : AbstractWebDriverEventListener(), Markers {

    override fun beforeClickOn(element: WebElement?, driver: WebDriver?) {
       highlightElement(element!!, driver!!)
    }
}
