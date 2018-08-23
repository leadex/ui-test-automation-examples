package javaselenium;

import com.automation.remarks.video.annotations.Video;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import javaselenium.rules.Configuration;
import org.junit.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utils.Allure;

import static java.lang.Thread.sleep;
import static javaselenium.TestActions.click;
import static javaselenium.TestActions.waitUntilVisible;
import static javaselenium.data.ElementsByText.byText;
import static javaselenium.data.ElementsByText.withText;

public class JavaSeleniumTest {

    @Rule
    public Configuration configuration = new Configuration();

    @Rule
    public Allure allure = Allure.INSTANCE;

    @Before
    public void before() {
        WebDriverContainer.getDriver().get("https://duckduckgo.com/");
    }

    @After
    public void after() {
        WebDriverContainer.getDriver().quit();
    }

    @Test
    @Video
    @DisplayName("java selenium")
    public void test() throws InterruptedException {
        fillingSearchBox("Golden Ratio");
        waitUntilVisible(byText("1.61803"), "Result", 10);
        clickFirstResult();
        click(withText("mathematics"), "Link");
        click(byText("Main page"), "Link");
        waitUntilVisible(withText("Wikipedia"), "Wikipedia Page Title", 10);
        checkLogoIsVisible();
        sleep(3000);
    }

    @Step("Filling Search Box with {text}")
    void fillingSearchBox(String text) {
        WebElement searchBox = WebDriverContainer.getDriver()
                .findElement(By.xpath("//input[@id='search_form_input_homepage']"));
        searchBox.sendKeys(text);
        searchBox.sendKeys(Keys.ENTER);
    }

    @Step("Pressing First Result Link")
    void clickFirstResult() {
        WebElement firstResult = WebDriverContainer.getDriver()
                .findElements(By.tagName("h2")).get(0);
        firstResult.click();
    }

    @Step("Checking Logo Title is visible")
    void checkLogoIsVisible() {
        WebElement logo = WebDriverContainer.getDriver()
                .findElement(By.xpath("/html[1]/body[1]/div[4]/div[2]/div[1]/a[1]"));
        waitUntilVisible(logo, "Logo", 10);
    }
}
