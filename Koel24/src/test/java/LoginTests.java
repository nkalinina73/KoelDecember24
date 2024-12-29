import org.testng.Assert;
import org.testng.annotations.Test;
import pagefactory.HomePage;
import pagefactory.LoginPage;

public class LoginTests extends BaseTest {
    @Test
    public void loginValidCredentials() {
        // Page Factory
        LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);


        navigateToPage();
        loginPage.provideEmail("natalia.kalinina@testpro.io").providePassword("nkKoel24$").clickSubmit();

        Assert.assertTrue(homePage.isAvatarDisplayed());

//========POM===========================================================

    /*    LoginPage loginPage = new LoginPage(driver);
        HomePage homePage = new HomePage(driver);
       loginPage.provideEmail("natalia.kalinina@testpro.io");
        loginPage.providePassword("nkKoel24$");
        loginPage.clickSubmit();

        Assert.assertTrue(homePage.getUserAvatar().isDisplayed());*/

// ===================================================================================
//      Added ChromeOptions argument below to fix websocket error
    /*    ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        String url = "https://qa.koel.app";
        driver.get(url);
        Assert.assertEquals(driver.getCurrentUrl(), url);
        driver.quit();*/
        //=====================================================



    }
}
