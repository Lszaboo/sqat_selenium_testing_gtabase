import org.openqa.selenium.*;

public abstract class GTASite extends SiteBase{
    
    protected static final By homeSiteLoc = By.xpath("//a[contains(@title,'GTA Base') and @href='/']"); 
    protected static final By logoutBtnLoc = By.xpath("//*[contains(@class,'user-navigation')]//*[contains(@href,'/logout/')]");
    protected static final By loginLoc = By.xpath("//a[contains(@href,'/login/')]"); 
    protected static final By searchBarTogglerLoc = By.xpath("//div[@class='dropdown search-toggle']/div[@data-bs-toggle='dropdown']");
    protected static final By searchInpLoc = By.xpath("//input[@name='q' and @type='text' and @id='mod-finder-searchword235']");
    protected static final By searchBtnLoc = By.xpath("//button[@type='submit']");

    protected String searchPhrase = "";

    public GTASite(WebTools wTools){
        super(wTools);
    }

    public MainSite clickGTABaseLogo(){
        WebElement returnElement = wait4AndGetElement(homeSiteLoc);
        returnElement.click();
        return new MainSite(wTools);
    }

    public MainSite clickBannerLogoutButton(){
        WebElement logoutBtn = wait4AndGetElement(logoutBtnLoc);
        logoutBtn.click();
        return new MainSite(wTools);
    }

    public LogInSite clickBannerLoginButton(){
        WebElement loginElement = wait4AndGetElement(loginLoc);
        loginElement.click();
        return new LogInSite(wTools);
    }

    public void clickMagnifyingGlass(){
        WebElement searchBarTogglerBtn = wait4AndGetElement(searchBarTogglerLoc);
        searchBarTogglerBtn.click();
    }

    public void enterSearchPhrase(String searchPhrase){
        this.searchPhrase = searchPhrase;
        WebElement searchInp = wait4AndGetHiddenElement(searchInpLoc);
        wTools.getJS().executeScript("arguments[0].value = arguments[1];",searchInp,searchPhrase);
    }

    public SearchResultsSite clickSearchButton(){
        WebElement searchBtn = wait4AndGetHiddenElement(searchBtnLoc);
        wTools.getJS().executeScript("arguments[0].click();",searchBtn);
        return new SearchResultsSite(wTools,searchPhrase);
    }
}
