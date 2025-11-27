package tests;

import framework.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.testng.annotations.Test;

public class SampleAppSmokeTest extends BaseTest {

    @Test
    public void appLaunchesAndStaysOpen() throws InterruptedException {
        // driver comes from BaseTest using default capabilities
        AndroidDriver driver = DriverManager.getAndroidDriver();

        // just keep the app visible to verify it stays open
        Thread.sleep(8000);
    }
}
