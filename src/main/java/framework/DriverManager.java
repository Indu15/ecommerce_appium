package framework;
// This class belongs to the 'framework' package — meaning it's part of the core framework utilities.

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

// Manages creating, storing, and stopping the Appium Android driver
public class DriverManager {

    // Holds the current AndroidDriver instance in memory
    private static AndroidDriver driver;

    // Returns the existing driver; throws error if someone calls it before starting driver
    public static AndroidDriver getAndroidDriver() {
        if (driver == null) throw new IllegalStateException("Driver not started");
        return driver;
    }

    @SuppressWarnings("unchecked")
    public static void startAndroid(String capsJsonPath) {
        try {
            // Log the JSON file path used for capabilities
            System.out.println("Loading capabilities from: " + capsJsonPath);

            String path = capsJsonPath; // The JSON file path
            String profile = null;      // Optional "profile" inside JSON (e.g., default, pixel7, emulator)

            // Check if the path contains "#", meaning a profile is specified (e.g., caps.json#pixel7)
            if (capsJsonPath != null && capsJsonPath.contains("#")) {
                String[] parts = capsJsonPath.split("#", 2);
                path = parts[0];      // actual file path
                profile = parts[1];   // selected profile name
            }

            // Load the JSON file into a Map (your Config class handles file reading)
            Map<String, Object> caps = Config.loadCaps(path);
            System.out.println("Raw capabilities loaded: " + caps);

            // Some JSON files contain nested maps for multiple profiles
            Map<String, Object> selectedCaps = null;

            // Check if any value in the JSON is itself a Map → means profile mode
            boolean hasNested = caps.values().stream().anyMatch(v -> v instanceof Map);

            if (hasNested) {
                // If a specific profile is selected, and exists in JSON → use that
                if (profile != null && caps.containsKey(profile) && caps.get(profile) instanceof Map) {
                    selectedCaps = (Map<String, Object>) caps.get(profile);
                }
                // Otherwise fallback to default profile
                else if (caps.containsKey("default") && caps.get("default") instanceof Map) {
                    selectedCaps = (Map<String, Object>) caps.get("default");
                }
                // If no "profile" or "default", pick the first Map available
                else {
                    for (Object v : caps.values()) {
                        if (v instanceof Map) {
                            selectedCaps = (Map<String, Object>) v;
                            break;
                        }
                    }
                }
            } else {
                // If JSON has no nested profiles, treat the whole JSON as the capability set
                selectedCaps = caps;
            }

            // If selectedCaps is still null — means JSON is invalid or empty
            if (selectedCaps == null) {
                throw new RuntimeException("No capabilities profile found in: " + path);
            }

            System.out.println("Using capabilities: " + selectedCaps);

            // Create the UiAutomator2 capability object Appium needs
            UiAutomator2Options options = new UiAutomator2Options();

            // Set required defaults (JSON can override these)
            options.setPlatformName("Android");
            options.setAutomationName("UiAutomator2");

            // Load optional values from JSON
            if (selectedCaps.containsKey("deviceName"))
                options.setDeviceName(selectedCaps.get("deviceName").toString());

            if (selectedCaps.containsKey("platformVersion"))
                options.setPlatformVersion(selectedCaps.get("platformVersion").toString());

            if (selectedCaps.containsKey("app")) {
                String appPath = selectedCaps.get("app").toString();
                if (appPath != null && !appPath.isBlank()) {
                    options.setApp(appPath); // Path to the .apk or .aab
                }
            }

            if (selectedCaps.containsKey("appPackage"))
                options.setAppPackage(selectedCaps.get("appPackage").toString());

            if (selectedCaps.containsKey("appActivity"))
                options.setAppActivity(selectedCaps.get("appActivity").toString());

            if (selectedCaps.containsKey("udid"))
                options.setUdid(selectedCaps.get("udid").toString());

            if (selectedCaps.containsKey("autoGrantPermissions"))
                options.setAutoGrantPermissions(Boolean.parseBoolean(selectedCaps.get("autoGrantPermissions").toString()));

            if (selectedCaps.containsKey("noReset"))
                options.setNoReset(Boolean.parseBoolean(selectedCaps.get("noReset").toString()));

            // Read Appium server URL (default is localhost)
            String url = selectedCaps.getOrDefault("serverUrl", "http://127.0.0.1:4723").toString();
            System.out.println("Connecting to Appium server at: " + url);
            System.out.println("Options: " + options);

            // Initialize the actual AndroidDriver
            driver = new AndroidDriver(new URL(url), options);

            // Set a global implicit wait
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

            System.out.println("AndroidDriver initialized successfully");

        } catch (Exception e) {
            System.err.println("Error details: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to start AndroidDriver: " + e.getMessage(), e);
        }
    }

    // Stops (quits) the driver and sets it to null so next test can recreate it
    public static void stop() {
        if (driver != null) {
            driver.quit();
            driver = null; // important to avoid stale reference
        }
    }
}