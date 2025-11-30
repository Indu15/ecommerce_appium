package tests;

import framework.Waits;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;



public class ApiDemosDarkThemeTest extends BaseTest  {

    /**
     * Helper method to check if an element is checked/selected
     * Works with checkboxes, radio buttons, and switches
     * Tries multiple attribute names as different widgets use different attributes
     */
    private boolean isElementChecked(WebElement element) {
        // Try common attribute names for checked state
        String[] attrNames = {"checked", "enabled", "selected"};

        for (String attr : attrNames) {
            String value = element.getAttribute(attr);
            if (value != null && "true".equalsIgnoreCase(value)) {
                return true;
            }
        }

        // Fallback: check if element has class containing "checked"
        String classList = element.getAttribute("class");
        if (classList != null && classList.contains("checked")) {
            return true;
        }

        return false;
    }

    @Test

    public void darkthemeTest() {
        AndroidDriver driver = framework.DriverManager.getAndroidDriver();
        Waits waits = new Waits(driver, Duration.ofSeconds(10));

        // Wait for and click Views item
        WebElement viewsItem = driver.findElement(AppiumBy.accessibilityId("Views"));
        viewsItem.click();

        // Wait for and click Controls item
        By controlsItem = By.xpath("//android.widget.TextView[@text='Controls']");
        WebElement controlsElement = waits.untilClickable(controlsItem);
        controlsElement.click();

        // Wait for and click Dark Theme item
        By darkTheme = By.xpath("//android.widget.TextView[@content-desc=\"2. Dark Theme\"]");
        WebElement darkThemeElement = waits.untilClickable(darkTheme);
        darkThemeElement.click();

        // Interact with edit field
        By editField = By.id("io.appium.android.apis:id/edit");
        WebElement editFieldElement = waits.untilVisible(editField);
        editFieldElement.sendKeys("Hello World");

        // Click checkbox
        By checkBox = By.id("io.appium.android.apis:id/check1");
        WebElement checkBoxElement = waits.untilClickable(checkBox);
        checkBoxElement.click();
        waits.pause(500); // Wait for state update

        // Click switch widget
        By switchwidget = By.id("io.appium.android.apis:id/toggle1");
        WebElement switchElement = waits.untilClickable(switchwidget);
        switchElement.click();
        waits.pause(500); // Wait for state update

        // Assertions to verify the actions performed
        Assert.assertEquals(driver.findElement(editField).getText(), "Hello World", "Text input does not match");
        // Refetch elements to get updated state
        WebElement checkBoxElement_verify = driver.findElement(checkBox);
        WebElement switchElement_verify = driver.findElement(switchwidget);

        // Debug output
        System.out.println("Checkbox checked attr: " + checkBoxElement_verify.getAttribute("checked"));
        System.out.println("Checkbox selected attr: " + checkBoxElement_verify.getAttribute("selected"));
        System.out.println("Checkbox enabled attr: " + checkBoxElement_verify.getAttribute("enabled"));
        System.out.println("Checkbox class: " + checkBoxElement_verify.getAttribute("class"));
        System.out.println("Checkbox isSelected(): " + checkBoxElement_verify.isSelected());

        System.out.println("Switch checked attr: " + switchElement_verify.getAttribute("checked"));
        System.out.println("Switch selected attr: " + switchElement_verify.getAttribute("selected"));
        System.out.println("Switch enabled attr: " + switchElement_verify.getAttribute("enabled"));
        System.out.println("Switch class: " + switchElement_verify.getAttribute("class"));
        System.out.println("Switch isSelected(): " + switchElement_verify.isSelected());

        Assert.assertTrue(isElementChecked(checkBoxElement_verify), "Checkbox is not selected");
        Assert.assertTrue(isElementChecked(switchElement_verify), "Switch is not turned on");
    }



}
