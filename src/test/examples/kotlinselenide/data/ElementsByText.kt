package kotlinselenide.data

import com.codeborne.selenide.Selectors
import com.codeborne.selenide.Selenide.`$$`
import com.codeborne.selenide.Selenide.`$`
import com.codeborne.selenide.SelenideElement
import kotlinselenide.allure.TitleProvider

object ElementsByText : TitleProvider {

    fun lastByText(text: String): SelenideElement {
        val collection = `$$`(Selectors.byText(text))
        return if (collection.size > 1) collection.last() title text
        else `$`(Selectors.byText(text)) title text
    }

    fun byText(text: String, index: Int) = `$$`(Selectors.byText(text))[index] title text
    fun withText(text: String) = `$`(Selectors.withText(text)) title text
    fun withText(text: String, index: Int) = `$$`(Selectors.withText(text))[index] title text
}
