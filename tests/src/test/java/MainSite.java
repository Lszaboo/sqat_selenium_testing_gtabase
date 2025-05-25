import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class MainSite extends GTASite{
    
    protected static final By loginLoc = By.xpath("//a[contains(@href,'/login/')]"); 

    public MainSite(WebDriver driver){
        super(driver,"https://www.gtabase.com/");
    }

    public LogInSite go2Login(){
        WebElement loginElement = wait4AndGetElement(loginLoc);
        loginElement.click();
        return new LogInSite(driver);
    }

}
