package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ApiDemosHomePage {

    private final AndroidDriver driver;

    // Main menu entry: "Views"
    // This works in classic ApiDemos where the list items are TextViews.
    private final By viewsMenuItem = By.xpath("//android.widget.TextView[@text='Views']");

    public ApiDemosHomePage(AndroidDriver driver) {
        this.driver = driver;
    }

    public void openViews() {
        WebElement views = driver.findElement(viewsMenuItem);
        views.click();
    }
}
