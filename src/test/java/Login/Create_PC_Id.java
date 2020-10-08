package Login;

import basePage.BasePage;
import dataProviders.excelUtils.ExcelKeys;
import dataProviders.excelUtils.ExcelReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.chrome.ChromeDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Create_PC_Id extends BasePage {

    public Create_PC_Id(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
    static Logger log = LogManager.getLogger(Create_PC_Id.class);

    public static void main(String[] args) throws Exception {
        SoftAssert softassert = new SoftAssert();
        System.setProperty("webdriver.chrome.driver", "F:\\Selenium\\chromedriver_win32\\chromedriver.exe");

        WebDriver driver = new ChromeDriver();
        driver.get("https://www.loblaws.ca");
        log.trace("Launched Loblaws.ca page");

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        WebElement element = driver.findElement(By.xpath("//*[text()='My Shop']"));
        element.click();
        log.trace("Clicked on MY SHOP");

        WebElement signInElement = driver.findElement(By.className("header-account-links"));
        signInElement.click();
        log.trace("Clicked on Sign In");

        WebElement createPCId = driver.findElement(By.cssSelector("a[href='/create-account']"));
        createPCId.click();
        log.trace("Clicked on Create Pc Id");

        String sUserName = ExcelReader.getDatasetValue(ExcelKeys.Login_ID, 1);
        String sPassword = ExcelReader.getDatasetValue(ExcelKeys.Login_Password, 1);

        WebElement email = driver.findElement(By.id("email"));
        WebElement password = driver.findElement(By.id("newPassword"));
        WebElement confirmPassword = driver.findElement(By.id("confirmPassword"));

        sendKeys(email, sUserName);
        sendKeys(password, sPassword);
        sendKeys(confirmPassword, sPassword);

        driver.findElement(By.id("termsAndConditions")).click();
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        log.trace("Clicked on Create PC Id");

        WebDriverWait wait = new WebDriverWait(driver, 20);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[@class='account-sync-page__title']")));

        WebElement completeProfile = driver.findElement(By.xpath("//*[@class='account-sync-page__title']"));

        softassert.assertEquals(completeProfile.getText(), "Complete Your Profile");
        driver.quit();
        System.out.println("Execution complete");
    }
}
