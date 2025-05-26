import org.openqa.selenium.*;

public class MyProfileSite extends GTASite{
    
    @Override
    protected String expectedUrl(){
        return "https://www.gtabase.com/my-profile/";
    }

    //*[contains(@class,'field-entry') and .//*[contains(text(),'Username')]]//*[not(contains(text(),'Username'))]
    //protected static final By accNameLocc = By.xpath("//*[contains(@class,'contact-name')]");

    protected static final By accInfoLoc = By.xpath("//*[contains(@id,'users-profile-core')]");
    protected static final By accUserNameLoc = By.xpath(accInfoXPath("Username"));
    protected static final By accDisplayNameLoc = By.xpath(accInfoXPath("Display Name"));
    protected static final By accEmailAddressNameLoc = By.xpath(accInfoXPath("Email Address"));
    protected static final By accBioLoc = By.xpath(accInfoXPath("Bio"));
    protected static final By editProfileBtnLoc = By.xpath("//a[contains(@href,'edit_profile') and contains(@class,'btn')]");
    protected static final By alertMsgLoc = By.xpath("//div[@class='alert-message']");

    protected static String accInfoXPath(String fieldName){        
        return "//*[contains(@id,'users-profile')]" + 
        "//*[contains(@class,'field-entry') and .//*[contains(text(),'" + 
        fieldName + 
        "')]]//*[not(contains(text(),'" + 
        fieldName + 
        "'))]";
    }


    public MyProfileSite(WebTools wTools){
        super(wTools);        
    }

    public String getAccountInfoText(){
        WebElement accInfo = wait4AndGetElement(accInfoLoc);
        return accInfo.getText();
    }

    public String getAccountUserName(){
        WebElement accUserName = wait4AndGetElement(accUserNameLoc);
        return accUserName.getText();
    }

    public String getAccountDisplayName(){
        WebElement accDisplayName = wait4AndGetElement(accDisplayNameLoc);
        return accDisplayName.getText();
    }

    public String getAccountEmailAddress(){
        WebElement accEmailAddress = wait4AndGetElement(accEmailAddressNameLoc);
        return accEmailAddress.getText();
    }

    public String getAccountBioText(){
        WebElement accBio = wait4AndGetElement(accBioLoc);
        return accBio.getText();
    }

    public EditProfileSite clickEditProfileButton(){
        WebElement editProfileBtn = wait4AndGetElement(editProfileBtnLoc);
        editProfileBtn.click();
        return new EditProfileSite(wTools);
    }

    public String getAlertMessageText(){
        WebElement alertMsg = wait4AndGetElement(alertMsgLoc);
        return alertMsg.getText();
    }

}
