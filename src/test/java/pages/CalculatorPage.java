package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class CalculatorPage {

    private final AndroidDriver driver;

    // Google Calculator typical resource-ids
    private final By digit2 = By.id("com.google.android.calculator:id/digit_2");
    private final By digit3 = By.id("com.google.android.calculator:id/digit_3");
    private final By plus   = By.id("com.google.android.calculator:id/op_add");
    private final By equals = By.id("com.google.android.calculator:id/eq");
    private final By result = By.id("com.google.android.calculator:id/result_final");

    public CalculatorPage(AndroidDriver driver) {
        this.driver = driver;
    }

    private void tap(By locator) {
        WebElement el = driver.findElement(locator);
        el.click();
    }

    public void calculateTwoPlusThree() {
        tap(digit2);
        tap(plus);
        tap(digit3);
        tap(equals);
    }

    public String getResultText() {
        return driver.findElement(result).getText();
    }
}
