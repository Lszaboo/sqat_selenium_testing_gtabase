import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

public class WebTools {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected JavascriptExecutor js;

    public static WebTools fromDriver(WebDriver driver){
        WebDriverWait wait = new WebDriverWait(driver, 10);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return new WebTools(driver, wait, js);
    }

    public WebTools(WebDriver driver, WebDriverWait wait, JavascriptExecutor js){
        this.driver = driver;
        this.wait = wait;
        this.js = js;
    }

    public WebDriver getDriver(){
        return driver;
    }

    public WebDriverWait getWait(){
        return wait;
    }

    public JavascriptExecutor getJS(){
        return js;
    }

}
