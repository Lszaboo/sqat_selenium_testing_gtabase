import java.util.List;

import javax.sound.midi.SysexMessage;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class GTASite extends SiteBase{
    
    protected static final By homeSiteLoc = By.xpath("//a[contains(@title,'GTA Base') and @href='/']"); 
    protected static final By logoutBtnLoc = By.xpath("//*[contains(@class,'user-navigation')]//*[contains(@href,'/logout/')]");
    protected static final By loginLoc = By.xpath("//a[contains(@href,'/login/')]"); 
    protected static final By searchBarTogglerLoc = By.xpath("//div[@class='dropdown search-toggle']/div[@data-bs-toggle='dropdown']");
    protected static final By searchInpLoc = By.xpath("//input[@name='q' and @type='text' and @id='mod-finder-searchword235']");
    protected static final By searchBtnLoc = By.xpath("//button[@type='submit']");

    public GTASite(WebTools wTools){
        super(wTools);
    }

    public MainSite return2MainSite(){
        WebElement returnElement = wait4AndGetElement(homeSiteLoc);
        returnElement.click();
        return new MainSite(wTools);
    }

    public MainSite doLogOut(){
        WebElement logoutBtn = wait4AndGetElement(logoutBtnLoc);
        logoutBtn.click();
        return new MainSite(wTools);
    }
//div[@id='search-result-list']//div[@class='item-info']/h2/a[contains(text(),'Entity')]
//div[@id='search-result-list']//div[@class='item-info']//h2[contains(text(),Entity)]
//div[@id='search-result-list']/div[1]//h2
    public LogInSite go2Login(){
        WebElement loginElement = wait4AndGetElement(loginLoc);
        loginElement.click();
        return new LogInSite(wTools);
    }

    public SearchResultsSite doSearch(String search){
        WebElement searchBarTogglerBtn = wait4AndGetElement(searchBarTogglerLoc);
        searchBarTogglerBtn.click();

        WebElement searchInp = wait4AndGetHiddenElement(searchInpLoc);
        WebElement searchBtn = wait4AndGetHiddenElement(searchBtnLoc);

        wTools.getJS().executeScript("arguments[0].value = arguments[1];",searchInp,search);      
        wTools.getJS().executeScript("arguments[0].click();",searchBtn);


        //searchInp.sendKeys(search);
        //searchBtn.click();

        return new SearchResultsSite(wTools,search);
    }
}
