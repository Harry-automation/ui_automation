package basePage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.PageFactory;


public class BasePage {
    protected static WebDriver driver;
    private static final Logger log = LogManager.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public static void sendKeys(WebElement element, String value) {
        element.click();
        element.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), Keys.DELETE);
        element.sendKeys(value);
    }

    public static void jsClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click();", element);
    }

}
