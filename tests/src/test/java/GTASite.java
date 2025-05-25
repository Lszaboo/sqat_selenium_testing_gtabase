import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class GTASite extends SiteBase{
    
    protected static final By homeSiteLoc = By.xpath("//a[contains(@title,'GTA Base') and @href='/']"); 
    
    public GTASite(WebDriver driver){
        super(driver);
    }

    public GTASite(WebDriver driver, String url){
        super(driver,url);
    }

    public MainSite return2MainSite(){
        WebElement returnElement = wait4AndGetElement(homeSiteLoc);
        returnElement.click();
        return new MainSite(driver);
    }
}
