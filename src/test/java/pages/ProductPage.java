
package pages;

import framework.DriverManager;
import framework.Waits;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.time.Duration;

public class ProductPage {
    private final Waits waits;

    private final By addToCart = AppiumBy.accessibilityId("add-to-cart"); // TODO replace
    private final By goToCart = AppiumBy.accessibilityId("cart-button");   // TODO replace

    public ProductPage() {
        this.waits = new Waits(DriverManager.getAndroidDriver(), Duration.ofSeconds(10));
    }

    public void addToCart() {
        waits.untilClickable(addToCart).click();
    }

    public void goToCart() {
        waits.untilClickable(goToCart).click();
    }
}
