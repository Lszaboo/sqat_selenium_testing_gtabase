import org.openqa.selenium.*;

public class LogInSite extends GTASite {
    
    @Override
    protected String expectedUrl(){
        return "https://www.gtabase.com/login/";
    }

    protected static final By usrInpLoc = By.xpath("//input[contains(@name,'username')]");
    protected static final By pwdInpLoc = By.xpath("//input[contains(@name,'password')]");
    protected static final By loginBtnLoc = By.xpath("//button[contains(@type,'submit') and contains(text(),'Log in')]");

    public LogInSite(WebTools wTools){
        super(wTools);
    }

    public MyProfileSite doLogIn(String usr, String pwd){
        WebElement usrInp = wait4AndGetElement(usrInpLoc);
        WebElement pwdInp = wait4AndGetElement(pwdInpLoc);
        WebElement loginBtn = wait4AndGetElement(loginBtnLoc);

        usrInp.sendKeys(usr);
        pwdInp.sendKeys(pwd);
        loginBtn.click();

        return new MyProfileSite(wTools);
    }
}
