import org.openqa.selenium.*;

public class EditProfileSite extends GTASite{
    @Override
    protected String expectedUrl(){
        return "https://www.gtabase.com/my-profile/?layout=edit_profile";
    }

    protected static final By bioTxTAreaLoc = By.xpath("//*[@id='jform_profile_aboutme']");
    protected static final By saveProfileBtnLoc = By.xpath("(//button[@type='submit' and @value='profile.save'])[1]");

    public EditProfileSite(WebTools wTools){
        super(wTools);
    }

    public void enterBio(String text){
        WebElement bioTxTArea = wait4AndGetElement(bioTxTAreaLoc);
        bioTxTArea.clear();
        bioTxTArea.sendKeys(text);
    }

    public MyProfileSite clickSaveEdits(){
        WebElement saveProfileBtn = wait4AndGetElement(saveProfileBtnLoc);
        saveProfileBtn.click();
        return new MyProfileSite(wTools);
    }

}
