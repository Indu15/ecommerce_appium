package tests;

import framework.DriverManager;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.WalmartHomePage;

import java.util.List;

public class WalmartSearchTest extends BaseTest {

    private WalmartHomePage homePage;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        // BaseTest will already start the driver
        this.homePage = new WalmartHomePage(DriverManager.getAndroidDriver());
    }

    @Test
    public void searchOrganicMilkAndPrint() {
        List<String> titles = homePage.searchAndGetResults("organic milk");
        System.out.println("Found " + titles.size() + " items matching organic milk:");
        titles.forEach(t -> System.out.println(" - " + t));
    }

    @AfterClass(alwaysRun = true)
    public void tearDownClass() {
        // BaseTest handles driver stop
    }
}
