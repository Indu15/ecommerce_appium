
package pages;

import framework.DriverManager;
import framework.Waits;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.time.Duration;

public class CartPage {
    private final Waits waits;
    private final By proceedToCheckout = AppiumBy.accessibilityId("proceed-to-checkout"); // TODO replace

    public CartPage() {
        this.waits = new Waits(DriverManager.getAndroidDriver(), Duration.ofSeconds(10));
    }

    public void proceedToCheckout() {
        waits.untilClickable(proceedToCheckout).click();
    }
}
