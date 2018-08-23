package javaselenium;

import io.qameta.allure.Step;
import org.jetbrains.annotations.NotNull;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class TestActions {

    @Step("Pressing {title}")
    static void click(WebElement webElement, String title) {
        webElement.click();
    }


    @Step("Checking visibility {title}")
    static void waitUntilVisible(WebElement element, String title, long timeout) {
        markElement(element);
        new WebDriverWait(WebDriverContainer.getDriver(), timeout)
                .until(ExpectedConditions.visibilityOf(element));
    }

    private static void markElement(@NotNull WebElement e) {
        String cl = e.getAttribute("class");
        if (!cl.contains("Widget")) {
            try {
                JavascriptExecutor js = (JavascriptExecutor) WebDriverContainer.getDriver();
                js.executeScript("arguments[0].setAttribute('style', 'background: #68bf47');", e);
            } catch (StaleElementReferenceException ex) {
                //
            }
        }
    }
}
