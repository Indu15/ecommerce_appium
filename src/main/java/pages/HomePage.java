
package pages;

import framework.DriverManager;
import framework.Waits;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class HomePage {
    private final Waits waits;

    private final By searchField = AppiumBy.accessibilityId("search-input"); // TODO replace with your app's id
    private final By searchSubmit = AppiumBy.accessibilityId("search-submit"); // TODO replace with your app's id

    public HomePage() {
        this.waits = new Waits(DriverManager.getAndroidDriver(), Duration.ofSeconds(10));
    }

    public void search(String query) {
        WebElement field = waits.untilVisible(searchField);
        field.click();
        field.sendKeys(query);
        waits.untilClickable(searchSubmit).click();
    }
}
