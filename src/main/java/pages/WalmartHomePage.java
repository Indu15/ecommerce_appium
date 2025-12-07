package pages;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WalmartHomePage {

    private final AndroidDriver driver;
    private final WebDriverWait wait;

    // Candidate locators for the search input — update with Appium Inspector if you find a stable resource-id
    private final List<By> searchLocators = Arrays.asList(
            By.id("com.walmart.android:id/search_src_text"),
            By.id("com.walmart.android:id/search_edit_text"),
            By.id("com.walmart.android:id/search_box_text"),
            By.xpath("//android.widget.EditText[contains(@resource-id,'search') or contains(@content-desc,'Search')]")
    );

    // Candidate locators for product/result titles containing milk/organic keywords
    private final List<By> resultsLocators = Arrays.asList(
            By.xpath("//android.widget.TextView[contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'organic') " +
                    "or contains(translate(@text,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'milk')]"),
            By.xpath("//android.view.View[contains(translate(@content-desc,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'milk') " +
                    "or contains(translate(@content-desc,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'organic')]")
    );

    public WalmartHomePage(AndroidDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    private WebElement findFirstClickable(List<By> candidates) {
        for (By b : candidates) {
            try {
                List<WebElement> elems = driver.findElements(b);
                for (WebElement e : elems) {
                    if (e.isDisplayed() && e.isEnabled()) {
                        return e;
                    }
                }
            } catch (Exception ignored) {
                // try next locator
            }
        }
        return null;
    }

    public void search(String query) {
        try {
            // 1) Find something clickable that looks like a search box
            WebElement input = findFirstClickable(searchLocators);
            if (input == null) {
                input = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath("//android.widget.EditText"))
                );
            }

            // 2) Tap it – this usually opens/expands the real search field
            input.click();

            // 3) After click, the original element can become stale.
            //    Re-find a fresh EditText and type into that.
            WebElement activeInput = wait.until(
                    ExpectedConditions.elementToBeClickable(
                            By.xpath("//android.widget.EditText"))
            );
            activeInput.clear();
            activeInput.sendKeys(query + "\n");

        } catch (org.openqa.selenium.StaleElementReferenceException se) {
            // One retry if the element went stale mid-action
            try {
                WebElement activeInput = wait.until(
                        ExpectedConditions.elementToBeClickable(
                                By.xpath("//android.widget.EditText"))
                );
                activeInput.clear();
                activeInput.sendKeys(query + "\n");
            } catch (Exception inner) {
                throw new RuntimeException(
                        "Failed to perform search after retry - update selectors via Appium Inspector",
                        inner
                );
            }
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to perform search - update selectors using Appium Inspector",
                    e
            );
        }

        // Wait briefly for results to load using any of the candidate result locators
        boolean seen = false;
        for (By b : resultsLocators) {
            try {
                wait.until(ExpectedConditions.visibilityOfElementLocated(b));
                seen = true;
                break;
            } catch (Exception ignored) {
            }
        }
        if (!seen) {
            try {
                Thread.sleep(800);
            } catch (InterruptedException ignored) {
            }
        }
    }

    public List<String> getResultTitles() {
        for (By b : resultsLocators) {
            try {
                List<WebElement> items = driver.findElements(b);
                List<String> texts = items.stream()
                        .map(WebElement::getText)
                        .filter(t -> t != null && !t.isBlank())
                        .collect(Collectors.toList());
                if (!texts.isEmpty()) {
                    return texts;
                }
            } catch (Exception ignored) {
                // try next locator
            }
        }
        return List.of();
    }

    public List<String> searchAndGetResults(String query) {
        search(query);
        return getResultTitles();
    }
}
