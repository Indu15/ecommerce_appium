
package pages;

import framework.DriverManager;
import framework.Waits;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class SearchResultsPage {
    private final Waits waits;

    // TODO: update this locator to match a real result item in your app
    private final By firstResult = AppiumBy.androidUIAutomator(
            "new UiSelector().className(\"android.view.View\").index(0)");

    public SearchResultsPage() {
        this.waits = new Waits(DriverManager.getAndroidDriver(), Duration.ofSeconds(10));
    }

    public void openFirstProduct() {
        WebElement item = waits.untilClickable(firstResult);
        item.click();
    }
}
