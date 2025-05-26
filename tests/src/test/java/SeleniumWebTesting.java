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

    protected MyProfileSite loginFromLoginSite(LogInSite loginSite){
        loginSite.enterUserName(mainTc.user.userName);
        loginSite.enterPassword(mainTc.user.password);
        return loginSite.clickLoginButton();
    }

    protected MyProfileSite loginFromSite(GTASite site){
        MyProfileSite myProfileSite = loginFromLoginSite(site.clickBannerLoginButton());
        assertTrue(myProfileSite.siteLoadedCorrectly());
        assertTrue(myProfileSite.getAccountDisplayName().contains(mainTc.user.displayName));
        assertTrue(myProfileSite.getAccountUserName().contains(mainTc.user.userName));
        assertTrue(myProfileSite.getAccountEmailAddress().contains(mainTc.user.emailAddress));
        return myProfileSite;
    }

    protected void logoutFromSite(GTASite site){
        MainSite mainSite = site.clickBannerLogoutButton();
        assertTrue(mainSite.siteLoadedCorrectly());
    }

    protected SearchResultsSite searchFromSite(GTASite site, String searchPhrase){
        site.clickMagnifyingGlass();
        site.enterSearchPhrase(searchPhrase);
        SearchResultsSite searchResultSite = site.clickSearchButton();
        
        assertTrue(searchResultSite.siteLoadedCorrectly());
        return searchResultSite;
    }

    protected void loadStaticPageFromUrl(String staticUrl){
        StaticSite site = StaticSite.enter(mainWTools, staticUrl);
        assertTrue(site.siteLoadedCorrectly());
    }

    protected void editProfileBioFromSite(GTASite site,String bio){
        MyProfileSite myProfileSite = loginFromSite(site);
        EditProfileSite editProfileSite = myProfileSite.clickEditProfileButton();
        editProfileSite.enterBio(bio);
        myProfileSite = editProfileSite.clickSaveEdits();
        
        assertEquals("Profile saved.", myProfileSite.getAlertMessageText());
        assertEquals(bio.trim(), myProfileSite.getAccountBioText().trim());
    }

    protected SiteBase record(SiteBase site){
        history.push(site);
        return site;
    }

    @Test
    public void testSingleStaticPageLoad() throws Exception{
        mainTc = new TestConfig("configs/conf_single_static_url.json");
        loadStaticPageFromUrl(mainTc.getUrlAt(0));
    }
    
    @Test
    public void testMultipleStaticPageLoad() throws Exception{
        mainTc = new TestConfig("configs/conf_multiple_static_urls.json"); 
        for(int i =0; i<mainTc.getUrlsLenght(); i++){
            loadStaticPageFromUrl(mainTc.getUrlAt(i));
        }
    }
    
    @Test
    public void testCorrectTitle() throws Exception{
        MainSite mainS = MainSite.enter(mainWTools);
        assertEquals("GTA Base: Everything on GTA 6, GTA 5, RDR2 & Rockstar Games",mainS.getTitleText());
    }

    @Test
    public void testSuccessfulHandsomeJackLogin() throws Exception{
        mainTc = new TestConfig("configs/conf_handsome_jack.json");
        MainSite mainS = MainSite.enter(mainWTools);
        loginFromSite(mainS);
    }

    @Test 
    public void testSuccessfulHandsomeJackLogout() throws Exception{
        mainTc = new TestConfig("configs/conf_handsome_jack.json");
        MainSite mainSite = MainSite.enter(mainWTools);
        
        MyProfileSite myProfileSite = loginFromSite(mainSite);
        logoutFromSite(myProfileSite);
    }

    @Test 
    public void testSuccesfulSearch() throws Exception{
        MainSite mainSite = MainSite.enter(mainWTools);
        SearchResultsSite searchResultsSite = searchFromSite(mainSite, "Entity\n");
        assertTrue(searchResultsSite.getNumberOfSearchResults()>=1);        
    }

    @Test
    public void testDefinedProfileBioEdit() throws Exception{
        mainTc = new TestConfig("configs/conf_handsome_jack.json");
        MainSite mainSite = MainSite.enter(mainWTools);
        editProfileBioFromSite(mainSite,mainTc.user.bio);
    }

    @Test
    public void testRandomProfileBioEdit() throws Exception{
        mainTc = new TestConfig("configs/conf_handsome_jack.json");
        MainSite mainSite = MainSite.enter(mainWTools);
        editProfileBioFromSite(mainSite,"Handsome Jack's current favourite number is: " + (int)(Math.random() * 999));
    }

    @Test
    public void testLoginHistory() throws Exception{
        mainTc = new TestConfig("configs/conf_handsome_jack.json");
        MainSite mainSite = MainSite.enter(mainWTools);
        LogInSite logInSite = mainSite.clickBannerLoginButton();
        MyProfileSite myProfileSite = loginFromLoginSite(logInSite);
        myProfileSite.backToPrevPage();
        assertEquals("You are already logged in.", logInSite.getLogoutMsgText());
    }

    @Test
    public void testLongHistory() throws Exception{
        mainTc = new TestConfig("configs/conf_handsome_jack.json");
        MainSite mainSite = (MainSite)record(MainSite.enter(mainWTools));
        LogInSite logInSite = (LogInSite)record(mainSite.clickBannerLoginButton());
        MyProfileSite myProfileSite = (MyProfileSite)record(loginFromLoginSite(logInSite));
        SearchResultsSite searchResultsSite = (SearchResultsSite)record(searchFromSite(myProfileSite,"banshee"));
        SiteBase site = searchFromSite(searchResultsSite,"banshee gts");
        
        while (!history.empty()) {
            site.backToPrevPage();
            site = history.pop();
            assertTrue(site.siteLoadedCorrectly());
        }
    }

    @After
    public void close() {
        if (mainDriver != null) {
            mainDriver.quit();
        }
    }
}
