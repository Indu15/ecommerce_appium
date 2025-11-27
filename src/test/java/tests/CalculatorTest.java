package tests;

import framework.DriverManager;
import io.appium.java_client.android.AndroidDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CalculatorPage;

public class CalculatorTest extends BaseTest {

    @Test
    public void testTwoPlusThreeEqualsFive() throws InterruptedException {
        AndroidDriver driver = DriverManager.getAndroidDriver();
        CalculatorPage calc = new CalculatorPage(driver);

        calc.calculateTwoPlusThree();

        // small pause to let UI update
        Thread.sleep(1000);

        String result = calc.getResultText();
        System.out.println("Calculator result: " + result);

        // Some calculators show "5", some "5 =" or similar; be a bit flexible if needed
        Assert.assertTrue(
                result.contains("5"),
                "Expected result to contain '5' but was: " + result
        );
    }
}
