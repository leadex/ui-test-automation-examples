package javaselenium.data;

import javaselenium.WebDriverContainer;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Quotes;

public class ElementsByText {

    private static String NORMALIZE_SPACE_XPATH = "normalize-space(translate(string(.), '\t\n\r\u00a0', '    '))";

    public static WebElement byText(String text) {
        return WebDriverContainer.getDriver().findElement(By.xpath("//*[contains(text(), '" + text + "')]"));
    }

    public static WebElement withText(String text) {
        return WebDriverContainer.getDriver().findElement(By.xpath(".//*/text()[" + NORMALIZE_SPACE_XPATH + " = " + Quotes.escape(text) + "]/parent::*"));
    }
}
