
package pages;

import framework.DriverManager;
import framework.Waits;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.time.Duration;

public class CheckoutPage {
    private final Waits waits;

    private final By emailField = AppiumBy.accessibilityId("email");     // TODO replace
    private final By nameField = AppiumBy.accessibilityId("name");       // TODO replace
    private final By addressField = AppiumBy.accessibilityId("address"); // TODO replace
    private final By placeOrderBtn = AppiumBy.accessibilityId("place-order"); // TODO replace
    private final By successMsg = AppiumBy.accessibilityId("order-success");  // TODO replace

    public CheckoutPage() {
        this.waits = new Waits(DriverManager.getAndroidDriver(), Duration.ofSeconds(10));
    }

    public void fillGuestCheckout(String email, String name, String address) {
        waits.untilVisible(emailField).sendKeys(email);
        waits.untilVisible(nameField).sendKeys(name);
        waits.untilVisible(addressField).sendKeys(address);
    }

    public void placeOrder() {
        waits.untilClickable(placeOrderBtn).click();
    }

    public boolean isSuccess() {
        return waits.untilTextPresent(successMsg, "Thank you");
    }
}
