import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class MyProfileSite extends GTASite{
    
    //*[contains(@class,'field-entry') and .//*[contains(text(),'Username')]]//*[not(contains(text(),'Username'))]
    //protected static final By accNameLocc = By.xpath("//*[contains(@class,'contact-name')]");
    protected static final By accInfoLoc = By.xpath("//*[contains(@id,'users-profile-core')]");
    protected static final By accUserNameLoc = By.xpath(accInfoXPath("Username"));
    protected static final By accDisplayNameLoc = By.xpath(accInfoXPath("Display Name"));
    protected static final By accEmailAddressNameLoc = By.xpath(accInfoXPath("Email Address"));


    protected static String accInfoXPath(String fieldName){        
        return "//*[contains(@id,'users-profile-core')]" + 
        "//*[contains(@class,'field-entry') and .//*[contains(text(),'" + 
        fieldName + 
        "')]]//*[not(contains(text(),'" + 
        fieldName + 
        "'))]";
    }


    public MyProfileSite(WebDriver driver){
        super(driver);        
    }

    public String getAccInfoText(){
        WebElement accInfo = wait4AndGetElement(accInfoLoc);
        return accInfo.getText();
    }

    public String getAccUserName(){
        WebElement accUserName = wait4AndGetElement(accUserNameLoc);
        return accUserName.getText();
    }

    public String getAccDisplayName(){
        WebElement accDisplayName = wait4AndGetElement(accDisplayNameLoc);
        return accDisplayName.getText();
    }

    public String getAccEmailAddress(){
        WebElement accEmailAddress = wait4AndGetElement(accEmailAddressNameLoc);
        return accEmailAddress.getText();
    }

}
