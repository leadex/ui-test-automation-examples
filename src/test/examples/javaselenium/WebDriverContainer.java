package javaselenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

public class WebDriverContainer {

    private static EventFiringWebDriver driver = null;

    public static WebDriver getDriver() {
        return driver;
    }

    public static void setDriver(EventFiringWebDriver driver) {
        WebDriverContainer.driver = driver;
    }
}
