package kotlinselenide

import com.codeborne.selenide.Condition
import com.codeborne.selenide.Selenide
import com.codeborne.selenide.SelenideElement
import kotlinselenide.data.ElementsByText.lastByText
import org.openqa.selenium.Keys
import org.openqa.selenium.WebElement
import utils.Markers

interface TestActions : Markers {

    fun String.click() = lastByText(this).click()

    operator fun String.rangeTo(element: String) {
        lastByText(this).click()
        lastByText(element).click()
    }

    infix fun String.shouldBe(condition: Condition) = waitUntil(lastByText(this), condition)

    operator fun SelenideElement.rangeTo(element: String) {
        this.click()
        lastByText(element).click()
    }

    infix fun SelenideElement.set(value: String): SelenideElement {
        this.clear()
        this.sendKeys(value)
        return this
    }

    infix fun SelenideElement.send(key: Keys) {
        this.sendKeys(key)
    }

    infix fun SelenideElement.shouldBe(condition: Condition) = waitUntil(this, condition)
    infix fun SelenideElement.shouldHave(text: String) = waitUntil(this, Condition.have(Condition.text(text)))

    private fun waitUntil(se: SelenideElement, condition: Condition): SelenideElement {
        se.waitUntil(condition, com.codeborne.selenide.Configuration.timeout)
        if (se.has(Condition.visible)) {
            moveTo(se)
            markElement(se)
        }
        return se
    }

    private val scrollIntoViewOptions get()  = "{behavior: \"instant\", block: \"center\", inline: \"center\"}"

    private fun moveTo(element: WebElement?) {
        Selenide.`$`(element).scrollIntoView(scrollIntoViewOptions)
    }
}
