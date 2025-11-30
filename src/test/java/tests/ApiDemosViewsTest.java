package tests;

import framework.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.ApiDemosHomePage;

public class ApiDemosViewsTest extends BaseTest {

    @Test
    public void openViewsFromHome() {
        AndroidDriver driver = DriverManager.getAndroidDriver();
        ApiDemosHomePage home = new ApiDemosHomePage(driver);

        // tap "Views"
        home.openViews();

        // very simple sanity check: verify that something with text "Views" is in the new screen title/bar
        // You can refine this later based on what Inspector shows
        String pageSource = driver.getPageSource();
        System.out.println("Current page source length: " + pageSource.length());

        // Just assert that the word "Views" exists somewhere on the new screen
        Assert.assertTrue(
                pageSource.contains("Views"),
                "Expected to be on a Views-related screen, but 'Views' not found in page source"
        );
    }
}
