import org.openqa.selenium.*;

public class SearchResultsSite extends GTASite{
    
    public static By getResultLocAt(int index){
        return By.xpath("//div[@id='search-result-list']/div[" + index + "]//h2");
    }

    @Override
    protected String expectedUrl(){
        return "https://www.gtabase.com/search?q=" + searchWord;
    }
    
    protected static final By searchNumLoc = By.xpath("//div[@id='search-query-explained']");

    protected String searchWord;

    public SearchResultsSite(WebTools wTools,String searchWord){
        super(wTools);
        this.searchWord = searchWord;
    }

    public String getSearchResultNum(){
        WebElement searchResNum = wait4AndGetElement(searchNumLoc);
        return searchResNum.getText();
    }
 
}
