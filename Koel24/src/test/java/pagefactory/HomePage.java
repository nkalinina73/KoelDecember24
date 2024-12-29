package pagefactory;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {
    @FindBy(css = "img.avatar")
    WebElement avatarIcon;
    @FindBy(css = ".playlist:nth-child(3)")
    WebElement firstPlaylist;
    @FindBy(css = "[name='name']")
    WebElement playlistNameField;
    @FindBy(css = "div.success.show")
    WebElement popUpNotification;

    public HomePage(WebDriver givenDriver) {
        super(givenDriver);
    }

    public HomePage enterNewPlaylistName(String playlistName) {
        findElement(playlistNameField).sendKeys(playlistName);
        findElement(playlistNameField).sendKeys(Keys.CONTROL,"a",Keys.BACK_SPACE);
        findElement(playlistNameField).sendKeys(playlistName);
        findElement(playlistNameField).sendKeys(Keys.ENTER);
        findElement(popUpNotification); //wait for the popup notification for successful update of the playlist name
        return this;
    }

    // Changed approach for assert
    public String getPlaylistName () {return findElement(firstPlaylist).getText();}
    public boolean isAvatarDisplayed() { return findElement(avatarIcon).isDisplayed();}

}