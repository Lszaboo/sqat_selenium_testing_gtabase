import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.net.MalformedURLException;

public class Task3Test {

    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        driver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        driver.manage().window().maximize();

        wait = new WebDriverWait(driver, 10);
    }

    private final By bodyLoc = By.tagName("body");
    private final By flashLoc = By.id("flash-messages");

    private final By usrLoc = By.id("username");
    private final By pwdLoc = By.id("password");
    private final By loginLoc = By.cssSelector("button[type='submit']");
    private final By logoutLoc = By.cssSelector("a[href='/logout']");

    private WebElement waitVisibiiltyAndFindElement(By locator) {
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return this.driver.findElement(locator);
    }

    private void wait4Body(){
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(bodyLoc));
    }

    private void doLogin(String usr, String pwd){
        WebElement usrTextInput = waitVisibiiltyAndFindElement(usrLoc);
        WebElement pwdTextInput = waitVisibiiltyAndFindElement(pwdLoc);
        WebElement loginBtn = waitVisibiiltyAndFindElement(loginLoc);

        usrTextInput.sendKeys(usr);
        pwdTextInput.sendKeys(pwd);
        loginBtn.click();
    }

    private void assertFlashMsg(String msg){
        WebElement flashMsg = waitVisibiiltyAndFindElement(flashLoc);
        Assert.assertTrue(flashMsg.getText().contains(msg));
    }

    @Test
    public void task3Test() throws Exception{
        this.driver.get("https://www.gtabase.com/");
        this.wait4Body();
        
        Thread.sleep(5000);

        //this.doLogin("tomsmith", "SuperSecretPassword!");
        //this.wait4Body();
        //assertFlashMsg("You logged into a secure area!");
        //
        //WebElement logoutBtn = waitVisibiiltyAndFindElement(logoutLoc);
        //logoutBtn.click();
        //this.wait4Body();
        //assertFlashMsg("You logged out of the secure area!");

        //this.doLogin("tomsmithh", "SuperSecretPassword!");
        //this.wait4Body();
        //assertFlashMsg("Your username is invalid!");

        //this.doLogin("tomsmith", "SuperSecretPassword");
        //this.wait4Body();
        //assertFlashMsg("Your password is invalid!");
    }

    @After
    public void close() {
        if (driver != null) {
            driver.quit();
        }
    }
}
