//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
import org.apache.commons.collections.functors.ExceptionPredicate;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;

public class SeleniumWebTesting {

    protected RemoteWebDriver mainDriver;
    protected WebDriverWait mainWait;
    protected JavascriptExecutor mainJS;
    protected WebTools mainWTools;

    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();

        mainDriver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        mainDriver.manage().window().maximize();
        
        mainWait = new WebDriverWait(mainDriver, 10);
        
        mainJS = (JavascriptExecutor) mainDriver;
        mainWTools = new WebTools(mainDriver,mainWait,mainJS);
    }

    //private void assertFlashMsg(String msg){
    //    WebElement flashMsg = waitVisibiiltyAndFindElement(flashLoc);
    //    Assert.assertTrue(flashMsg.getText().contains(msg));
    //}

    protected TestConfig loadTestConfig(String path) throws Exception{
        return new TestConfig(path);
    }

    protected MyProfileSite testSuccesfulLogin(MainSite mainS,TestConfig tc){
        LogInSite loginS = mainS.go2Login();
        MyProfileSite myProfileS = loginS.doLogIn(tc.user.userName,tc.user.password);
        
        assertTrue(myProfileS.isSiteLoadedCorrectly());
        assertTrue(myProfileS.getAccDisplayName().contains(tc.user.displayName));
        assertTrue(myProfileS.getAccUserName().contains(tc.user.userName));
        assertTrue(myProfileS.getAccEmailAddress().contains(tc.user.emailAddress));

        return myProfileS;
    }

    protected void testSuccesfulLogout(GTASite site){
        MainSite mainS = site.doLogOut();
        assertTrue(mainS.isSiteLoadedCorrectly());
    }

    protected SearchResultsSite testSearch(GTASite site, String search){
        SearchResultsSite searchRessS = site.doSearch(search);
        assertTrue(searchRessS.isSiteLoadedCorrectly());
        return searchRessS;
    }

    //@Test
    public void testStaticPageLoad() throws Exception{
        TestConfig tc = loadTestConfig("configs/handsome_jack.json");
        MainSite mainS = MainSite.enter(mainWTools);
        assertTrue(mainS.isSiteLoadedCorrectly());
    }

    //@Test
    public void testCorrectTitle() throws Exception{
        TestConfig tc = loadTestConfig("configs/handsome_jack.json");
        MainSite mainS = MainSite.enter(mainWTools);
        assertEquals("GTA Base: Everything on GTA 6, GTA 5, RDR2 & Rockstar Games",mainS.getTitle());
    }

    //@Test
    public void testSuccessfulHandsomeJackLogin() throws Exception{
        TestConfig tc = loadTestConfig("configs/handsome_jack.json");
        MainSite mainS = MainSite.enter(mainWTools);
        testSuccesfulLogin(mainS,tc);
    }

    //@Test 
    public void testSuccessfulHandsomeJackLogout() throws Exception{
        TestConfig tc = loadTestConfig("configs/handsome_jack.json");
        MainSite mainS = MainSite.enter(mainWTools);
        
        MyProfileSite myProfileS = testSuccesfulLogin(mainS,tc);
        testSuccesfulLogout(myProfileS);
    }

    //@Test 
    public void testSuccesfulSearch() throws Exception{
        TestConfig tc = loadTestConfig("configs/handsome_jack.json");
        MainSite mainS = MainSite.enter(mainWTools);

        SearchResultsSite site = testSearch(mainS, "Entity\n");
        assertTrue(site.getSearchResultNum()>=1);        
    }



    @After
    public void close() {
        if (mainDriver != null) {
            mainDriver.quit();
        }
    }
}
