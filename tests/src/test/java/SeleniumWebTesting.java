//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.*;
import org.openqa.selenium.support.ui.*;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertTrue;

import java.net.MalformedURLException;

public class SeleniumWebTesting {

    protected RemoteWebDriver mainDriver;
    
    @Before
    public void setup() throws MalformedURLException {
        ChromeOptions options = new ChromeOptions();
        mainDriver = new RemoteWebDriver(new URL("http://selenium:4444/wd/hub"), options);
        mainDriver.manage().window().maximize();
    }

    //private void assertFlashMsg(String msg){
    //    WebElement flashMsg = waitVisibiiltyAndFindElement(flashLoc);
    //    Assert.assertTrue(flashMsg.getText().contains(msg));
    //}

    @Test
    public void testSuccessfulLogin() throws Exception{
        
        TestConfig tc = new TestConfig("configs/handsome_jack.json");

        MainSite mainS   = new MainSite(mainDriver);
        LogInSite loginS = mainS.go2Login();
        MyProfileSite profileS = loginS.doLogIn(tc.user.usr,tc.user.pwd);
        
        //System.out.println(profileS.getAccInfoText());
        assertTrue(profileS.getAccInfoText().contains("Handsome Jack"));
        assertTrue(profileS.getAccDisplayName().contains("Handsome Jack"));
        assertTrue(profileS.getAccUserName().contains("handsome.jack"));
        assertTrue(profileS.getAccEmailAddress().contains("jackhandsome.hyperion01@gmail.com"));



        Thread.sleep(5000);
    }

    @After
    public void close() {
        if (mainDriver != null) {
            mainDriver.quit();
        }
    }
}
