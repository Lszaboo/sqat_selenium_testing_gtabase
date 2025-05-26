import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Stack;

public class SeleniumWebTesting {

    protected RemoteWebDriver mainDriver;
    protected WebDriverWait mainWait;
    protected JavascriptExecutor mainJS;
    protected WebTools mainWTools;
    protected TestConfig mainTc;
    protected Stack<SiteBase> history;

    @Before
    public void setup() throws Exception {
        ChromeOptions options = new ChromeOptions();

        mainDriver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        mainDriver.manage().window().maximize();
        
        mainWait = new WebDriverWait(mainDriver, 10);
        
        mainJS = (JavascriptExecutor) mainDriver;
        mainWTools = new WebTools(mainDriver,mainWait,mainJS);
        
        mainTc = new TestConfig("configs/conf_empty.json");

        history = new Stack<SiteBase>();
    }

    protected MyProfileSite testSuccesfulLogin(MainSite mainS){
        LogInSite loginS = mainS.go2Login();
        MyProfileSite myProfileS = loginS.doLogIn(mainTc.user.userName,mainTc.user.password);
        
        assertTrue(myProfileS.isSiteLoadedCorrectly());
        assertTrue(myProfileS.getAccDisplayName().contains(mainTc.user.displayName));
        assertTrue(myProfileS.getAccUserName().contains(mainTc.user.userName));
        assertTrue(myProfileS.getAccEmailAddress().contains(mainTc.user.emailAddress));

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

    protected void testStaticPageLoad(String staticUrl){
        StaticSite site = StaticSite.enter(mainWTools, staticUrl);
        assertTrue(site.isSiteLoadedCorrectly());
    }

    protected void testProfileBioEdit(String bio){
        MainSite mSite = MainSite.enter(mainWTools);
        MyProfileSite mpSite = testSuccesfulLogin(mSite);
        EditProfileSite epSite = mpSite.clickEditProfile();
        epSite.rewriteBio(bio);
        mpSite = epSite.saveEdits();
        
        assertEquals("Profile saved.", mpSite.getAlertMessage());
        assertEquals(bio.trim(), mpSite.getAccBio().trim());
    }

    protected SiteBase record(SiteBase site){
        history.push(site);
        return site;
    }

    @Test
    public void testSingleStaticPageLoad() throws Exception{
        mainTc = new TestConfig("configs/conf_single_static_url.json");
        testStaticPageLoad(mainTc.getUrlAt(0));
    }
    
    @Test
    public void testMultipleStaticPageLoad() throws Exception{
        mainTc = new TestConfig("configs/conf_multiple_static_urls.json"); 
        for(int i =0; i<mainTc.getUrlLenght(); i++){
            testStaticPageLoad(mainTc.getUrlAt(i));
        }
    }
    
    @Test
    public void testCorrectTitle() throws Exception{
        MainSite mainS = MainSite.enter(mainWTools);
        assertEquals("GTA Base: Everything on GTA 6, GTA 5, RDR2 & Rockstar Games",mainS.getTitle());
    }

    @Test
    public void testSuccessfulHandsomeJackLogin() throws Exception{
        mainTc = new TestConfig("configs/conf_handsome_jack.json");
        MainSite mainS = MainSite.enter(mainWTools);
        testSuccesfulLogin(mainS);
    }

    @Test 
    public void testSuccessfulHandsomeJackLogout() throws Exception{
        mainTc = new TestConfig("configs/conf_handsome_jack.json");
        MainSite mainS = MainSite.enter(mainWTools);
        
        MyProfileSite myProfileS = testSuccesfulLogin(mainS);
        testSuccesfulLogout(myProfileS);
    }

    @Test 
    public void testSuccesfulSearch() throws Exception{
        MainSite mainS = MainSite.enter(mainWTools);
        SearchResultsSite site = testSearch(mainS, "Entity\n");
        assertTrue(site.getSearchResultNum()>=1);        
    }

    @Test
    public void testDefinedProfileBioEdit() throws Exception{
        mainTc = new TestConfig("configs/conf_handsome_jack.json");
        testProfileBioEdit(mainTc.user.bio);
    }

    @Test
    public void testRandomProfileBioEdit() throws Exception{
        mainTc = new TestConfig("configs/conf_handsome_jack.json");
        testProfileBioEdit("Handsome Jack's current favourite number is: " + (int)(Math.random() * 999));
    }

    @Test
    public void testLoginHistory() throws Exception{
        mainTc = new TestConfig("configs/conf_handsome_jack.json");
        MainSite mSite = MainSite.enter(mainWTools);
        LogInSite liSite = mSite.go2Login();
        MyProfileSite mpSite = liSite.doLogIn(mainTc.user.userName,mainTc.user.password);
        mpSite.backToPrevPage();
        assertEquals("You are already logged in.", liSite.getLogoutMsgText());
    }

    @Test
    public void testLongHistory() throws Exception{
        mainTc = new TestConfig("configs/conf_handsome_jack.json");
        MainSite mSite = (MainSite)record(MainSite.enter(mainWTools));
        LogInSite liSite = (LogInSite)record(mSite.go2Login());
        MyProfileSite mpSite = (MyProfileSite)record(liSite.doLogIn(mainTc.user.userName,mainTc.user.password));
        SearchResultsSite srSite = (SearchResultsSite)record(mpSite.doSearch("banshee"));
        SiteBase site = srSite.doSearch("banshee gts");
        
        while (!history.empty()) {
            site.backToPrevPage();
            site = history.pop();
            assertTrue(site.isSiteLoadedCorrectly());
        }
    }

    @After
    public void close() {
        if (mainDriver != null) {
            mainDriver.quit();
        }
    }
}
