import java.util.List;

import javax.sound.midi.SysexMessage;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;

public abstract class GTASite extends SiteBase{
    
    protected static final By homeSiteLoc = By.xpath("//a[contains(@title,'GTA Base') and @href='/']"); 
    protected static final By logoutBtnLoc = By.xpath("//*[contains(@class,'user-navigation')]//*[contains(@href,'/logout/')]");
    protected static final By loginLoc = By.xpath("//a[contains(@href,'/login/')]"); 
    //protected static final By searchBarTogglerLoc = By.xpath("//div[contains(@class,'dropdown search-toggle')]");
    //protected static final By searchBarTogglerLoc = By.xpath("//div[@class='dropdown search-toggle']/div[@data-bs-toggle='dropdown']");
    protected static final By searchBarTogglerLoc = By.xpath(
        "//*[@id=\"ja-mainnav\"]/div/div/div/div[1]"
    );

    protected static final By searchInpLoc = By.xpath("//input[@name='q' and @type='text' and @id='mod-finder-searchword235']");

    protected static final By searchBtnLoc = By.xpath("//button[@type='submit']");

    public GTASite(WebTools wTools){
        super(wTools);
    }

    public GTASite(WebTools wTools, String url){
        super(wTools,url);
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
        WebElement searchBarTogglerBtn = wait4AndGetElement(By.cssSelector(
            "div.dropdown-toggle.icon-search::before"
        ));
        wTools.getJS().executeScript("arguments[0].click()", searchBarTogglerBtn);
        searchBarTogglerBtn.click();
        //System.out.println(wTools.getDriver().getPageSource());
        
        //wTools.getJS().executeScript("arguments[0].click();", searchBarTogglerBtn);
        
        //searchBarTogglerBtn.click();
        
        List<WebElement> elements = wTools.getDriver().findElements(searchBarTogglerLoc);
        System.out.println(elements.size());
        elements.clear();
        
        elements = wTools.getDriver().findElements(searchInpLoc);
        System.out.println(elements.size());
        elements.clear();

        elements = wTools.getDriver().findElements(searchBtnLoc);
        System.out.println(elements.size());
        elements.clear();

        

        WebElement searchInp = wait4AndGetElement(searchInpLoc);
        WebElement searchBtn = wait4AndGetElement(searchBtnLoc);

        searchInp.sendKeys(search);
        searchBtn.click();

        return new SearchResultsSite(wTools,search);
    }
}
