package javaselenium.rules;

import javaselenium.WebDriverContainer;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import utils.Highlighter;

public class Configuration extends TestWatcher {

    @Override
    protected void starting(Description description) {
        String pathName = "webdriver.chrome.driver";
        ClassLoader loader = this.getClass().getClassLoader();
        String osName = System.getProperty("os.name").toLowerCase();

        String linChromeDriver = loader.getResource("chromedriver_linux64/chromedriver").getPath();
        String macChromeDriver = loader.getResource("chromedriver_mac64/chromedriver").getPath();
        String winChromeDriver = loader.getResource("chromedriver_win32/chromedriver.exe").getPath();

        System.setProperty(pathName, linChromeDriver);
        if (osName.contains("mac"))
            System.setProperty(pathName, macChromeDriver);
        else if (osName.contains("win"))
            System.setProperty(pathName, winChromeDriver);

        ChromeOptions options = new ChromeOptions();
        options.addArguments(
                "--start-maximized",
                "--ignore-certificate-errors",
                "--disable-popup-blocking",
                "--incognito");

        EventFiringWebDriver driver = new EventFiringWebDriver(new ChromeDriver(options));
        driver.register(Highlighter.INSTANCE);
        WebDriverContainer.setDriver(driver);
    }
}
