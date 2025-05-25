import org.openqa.selenium.*;

public class MainSite extends GTASite{
    
    @Override
    protected String expectedUrl(){
        return "https://www.gtabase.com/";
    }

    public MainSite(WebTools wTools){
        super(wTools,"https://www.gtabase.com/");
    }

}
