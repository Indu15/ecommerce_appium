
package tests;

import framework.DriverManager;
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.*;

public class BaseTest {
    @BeforeSuite
    public void beforeSuite() {
        // Hook for future env setup
    }

    @Parameters({"capsPath"})
    @BeforeClass
    public void setUp(@Optional("src/test/resources/capabilities/android.json#default") String capsPath) {
        DriverManager.startAndroid(capsPath);
    }

    @AfterMethod(alwaysRun = true)
    public void afterMethod(ITestResult result) {
        if (!result.isSuccess()) {
            takeScreenshot("failure");
        }
    }

    @AfterClass(alwaysRun = true)
    public void tearDown() {
        DriverManager.stop();
    }

    @Attachment(value = "Screenshot - {0}", type = "image/png")
    public byte[] takeScreenshot(String name) {
        try {
            return ((TakesScreenshot) DriverManager.getAndroidDriver()).getScreenshotAs(OutputType.BYTES);
        } catch (Exception e) {
            return new byte[0];
        }
    }
}
