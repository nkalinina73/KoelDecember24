import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.UUID;

import static org.openqa.selenium.By.cssSelector;

public class BaseTest {

    public static WebDriver driver = null;
    public String url = null;
    public WebDriverWait wait = null;
    public Actions actions = null;

    @DataProvider(name = "IncorrectLoginData")
    public Object[][] getDataFromDataProviders() {
        return new Object[][]{
                {"invalid@email.com", "invalidPSWD"},
                {"demo@class.com", ""},
                {"", ""}
        };
    }

    @BeforeSuite
    static void setupClass() {
        //WebDriverManager.firefoxdriver().setup();
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    @Parameters({"BaseURL"})
    public void launchClass(String BaseURL) throws MalformedURLException {
        //added ChromeOptions argument to fix websocket error
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");

        //driver = new ChromeDriver(options);
        //driver = new FirefoxDriver();
        driver = pickBrowser(System.getProperty("browser"));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        url = BaseURL;
        actions = new Actions(driver);
    }

    public static WebDriver pickBrowser (String browser) throws MalformedURLException {
        // Grid setup
        DesiredCapabilities caps = new DesiredCapabilities();
        String gridURL = "http://192.168.11.20:4444";

        switch (browser){
            case "firefox": //gradle clean test -Dbrowser=firefox
                WebDriverManager.firefoxdriver().setup();
                return driver = new FirefoxDriver();

            case "MicrosoftEdge"://gradle clean test -Dbrowser=MicrosoftEdge
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--remote-allow-origins=*");
                return driver = new EdgeDriver(edgeOptions);

            case "grid-edge":
                caps.setCapability("browserName", "MicrosoftEdge");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(), caps);

            case "grid-firefox":
                caps.setCapability("browserName", "firefox");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(), caps);

            case "grid-chrome":
                caps.setCapability("browserName", "chrome");
                return driver = new RemoteWebDriver(URI.create(gridURL).toURL(), caps);

            case "cloud":
                return lambdaTest();
            default:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--remote-allow-origins=*");
                return driver = new ChromeDriver(chromeOptions);
        }
    }

    @AfterMethod
    public void closeBrowser() {

        driver.quit();
    }

    public static WebDriver lambdaTest () throws MalformedURLException {
        String hubURL = "https://hub.lambdatest.com/wd/hub";

        ChromeOptions browserOptions = new ChromeOptions();
        browserOptions.setPlatformName("Windows 10");
        browserOptions.setBrowserVersion("beta");
        HashMap<String, Object> ltOptions = new HashMap<String, Object>();
        ltOptions.put("username", "nkalinina73");
        ltOptions.put("accessKey", "hQRiyeCpetOiYPfx6BgelF2Xg9OWv4cBZm1mdksfOLBcRmrJBV");
        ltOptions.put("project", "Untitled");
        ltOptions.put("w3c", true);
        ltOptions.put("plugin", "java-java");
        browserOptions.setCapability("LT:Options", ltOptions);

        return new RemoteWebDriver(new URL(hubURL), browserOptions);
    }// this lambdaTest() method returns an instance of WebDriver for remote testing using the LambdaTest service

    public void navigateToPage() {
        driver.get(url);
    }

    public void providePassword(String password) {
        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='password']")));
        passwordField.clear();
        passwordField.sendKeys(password);
    }

    public void provideEmail(String email) {
        WebElement emailField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[type='email']")));
        emailField.clear();
        emailField.sendKeys(email);
    }

    public void clickLoginButton() {
        WebElement buttonSubmit = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));
        buttonSubmit.click();
    }

/*    public void clickNextSongButton() {
        WebElement nextSongButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.next.fa.fa-step-forward.control")));

        //WebElement nextSongButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("i.next.fa.fa-step-forward.control")));
        nextSongButton.click();

    }*/
/*
    public void clickPlayButton() {
        WebElement nextSongButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.play")));
        nextSongButton.click();
    }*/

/*    public void validateSongIsPlaying() {
        WebElement songImage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#mainFooter img")));
        songImage.isDisplayed();

    }*/
/*    public void createNewPlaylist() {
        WebElement newPlaylistButton = wait.until(ExpectedConditions.elementToBeClickable(cssSelector("#playlists .fa.fa-plus-circle.create")));
        newPlaylistButton.click();
    }*/
/*    public void clickNewPlaylist() {
        WebElement newPlaylist = wait.until(ExpectedConditions.elementToBeClickable(cssSelector(" [data-testid='playlist-context-menu-create-simple']")));
        newPlaylist.click();
    }*/

/*    public void typeNameOfPlaylist(String name) {
        WebElement namePLField = wait.until(ExpectedConditions.visibilityOfElementLocated(cssSelector(".create input[type='text']")));
        namePLField.clear();
        namePLField.sendKeys(name);
        Actions actions = new Actions(driver);
        actions.sendKeys(namePLField, Keys.RETURN).perform();
    }*/

  /*  public void clickOnPlaylist() {
        WebElement playList = wait.until(ExpectedConditions.elementToBeClickable(By.linkText("NK_PL2")));
        playList.click();
    }*/

/*    public void clickDeleteButton() {
        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(cssSelector(".del.btn-delete-playlist")));
        deleteButton.click();
    }*/

/*    public String deletedPlaylistNotification() {
        WebElement deleteNotification = wait.until(ExpectedConditions.visibilityOfElementLocated(cssSelector(".alertify-logs.top.right")));
        return deleteNotification.getText();
    }*/


    public void clickAvatarIcon() {
        WebElement avatarIcon = driver.findElement(cssSelector("#userBadge img.avatar"));
        avatarIcon.click();
    }

    public void clickSaveButton() {
        WebElement saveButton = driver.findElement(cssSelector(".btn-submit"));
        saveButton.click();
    }

    public void provideProfileName(String profileName) {
        WebElement myPName = driver.findElement(cssSelector("#inputProfileName"));
        myPName.clear();
        myPName.sendKeys(profileName);
    }

    public void provideCurrentPassword(String currentPassword) {
        WebElement myPassword = driver.findElement(cssSelector("#inputProfileCurrentPassword"));
        myPassword.sendKeys(currentPassword);
    }

    public String generateRandomName() {
        return UUID.randomUUID().toString().replace("-", "");
    }

}