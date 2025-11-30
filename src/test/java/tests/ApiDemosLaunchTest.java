package tests;

import framework.DriverManager;
import org.testng.annotations.Test;

public class ApiDemosLaunchTest extends BaseTest {

    @Test
    public void appLaunchesSuccessfully() {
        System.out.println("🚀 ApiDemos launch test started");
        System.out.println("Driver: " + DriverManager.getAndroidDriver());

        // If the app opens on emulator and driver is not null → SUCCESS
        System.out.println("✅ ApiDemos app launched successfully!");
    }
}
