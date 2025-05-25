import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class LogInSite extends GTASite {
    
    protected static final By usrInpLoc = By.xpath("//input[contains(@name,'username')]");
    protected static final By pwdInpLoc = By.xpath("//input[contains(@name,'password')]");
    protected static final By loginBtnLoc = By.xpath("//button[contains(@type,'submit') and contains(text(),'Log in')]");

    public LogInSite(WebDriver driver){
        super(driver);
    }

    public MyProfileSite doLogIn(String usr, String pwd){
        WebElement usrInp = wait4AndGetElement(usrInpLoc);
        WebElement pwdInp = wait4AndGetElement(pwdInpLoc);
        WebElement loginBtn = wait4AndGetElement(loginBtnLoc);

        usrInp.sendKeys(usr);
        pwdInp.sendKeys(pwd);
        loginBtn.click();

        return new MyProfileSite(driver);
    }
}
