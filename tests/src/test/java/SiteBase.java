import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public abstract class SiteBase {
    
    protected WebTools wTools;

    protected static final By bodyLoc = By.tagName("body");
    
    protected abstract String expectedUrl();
    
    public SiteBase(WebTools wTools){
        this.wTools = wTools;        
        wait4Body();
    }

    public SiteBase(WebTools wTools, String url){
        this.wTools = wTools;
        
        this.wTools.getDriver().get(url);
        wait4Body();
    }

    public void wait4Element(By locator){
        wTools.getWait().until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement wait4AndGetElement(By locator){
        wait4Element(locator);
        return wTools.getDriver().findElement(locator);
    }

    public void wait4Body(){
        wait4Element(bodyLoc);
    }

    public boolean isUrlCorrect(){
        return wTools.getWait().until(ExpectedConditions.urlContains(expectedUrl()));
    }

    public boolean isSiteLoadedCorrectly(){
        boolean correctlyLoaded = isUrlCorrect();
        correctlyLoaded = correctlyLoaded && wTools.getWait().until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver driver) {
                return wTools.getJS().executeScript("return document.readyState").equals("complete");
            }
        });

        return correctlyLoaded;
    }

}
