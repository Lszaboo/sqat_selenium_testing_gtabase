import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class SiteBase {
    
    protected WebDriver driver;
    protected WebDriverWait wait;

    protected static final By bodyLoc = By.tagName("body");
    
    public SiteBase(WebDriver driver){
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        
        wait4Body();
    }

    public SiteBase(WebDriver driver, String url){
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        
        this.driver.get(url);
        wait4Body();
    }

    public void wait4Element(By locator){
        this.wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement wait4AndGetElement(By locator){
        wait4Element(locator);
        return this.driver.findElement(locator);
    }

    public void wait4Body(){
        wait4Element(bodyLoc);
    }

}
