package kotlinselenide.data

import com.codeborne.selenide.Selenide
import kotlinselenide.allure.StepBuilder
import kotlinselenide.allure.TitleProvider
import org.openqa.selenium.By

object Elements : TitleProvider {

    init {
        register(StepBuilder)
    }

    val searchBox get() = Selenide.`$`(By.xpath("//input[@id='search_form_input_homepage']")) title "Search Box"
    val firstResult get() = Selenide.`$$`(By.tagName("h2"))[0] title "First Result"
    val logo get() = Selenide.`$$`(By.xpath("/html[1]/body[1]/div[4]/div[2]/div[1]/a[1]"))[0] title "Logo"
}