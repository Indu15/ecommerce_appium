package framework;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import java.net.URL;
import java.time.Duration;
import java.util.Map;

public class DriverManager {
    private static AndroidDriver driver;

    public static AndroidDriver getAndroidDriver() {
        if (driver == null) throw new IllegalStateException("Driver not started");
        return driver;
    }

    @SuppressWarnings("unchecked")
    public static void startAndroid(String capsJsonPath) {
        try {
            System.out.println("Loading capabilities from: " + capsJsonPath);

            String path = capsJsonPath;
            String profile = null;
            if (capsJsonPath != null && capsJsonPath.contains("#")) {
                String[] parts = capsJsonPath.split("#", 2);
                path = parts[0];
                profile = parts[1];
            }

            Map<String, Object> caps = Config.loadCaps(path);
            System.out.println("Raw capabilities loaded: " + caps);

            // If the loaded JSON contains profile maps (values that are Maps), select one
            Map<String, Object> selectedCaps = null;
            boolean hasNested = caps.values().stream().anyMatch(v -> v instanceof Map);
            if (hasNested) {
                if (profile != null && caps.containsKey(profile) && caps.get(profile) instanceof Map) {
                    selectedCaps = (Map<String, Object>) caps.get(profile);
                } else if (caps.containsKey("default") && caps.get("default") instanceof Map) {
                    selectedCaps = (Map<String, Object>) caps.get("default");
                } else {
                    // pick the first nested map
                    for (Object v : caps.values()) {
                        if (v instanceof Map) {
                            selectedCaps = (Map<String, Object>) v;
                            break;
                        }
                    }
                }
            } else {
                selectedCaps = caps;
            }

            if (selectedCaps == null) {
                throw new RuntimeException("No capabilities profile found in: " + path);
            }

            System.out.println("Using capabilities: " + selectedCaps);

            UiAutomator2Options options = new UiAutomator2Options();
            // These are safe defaults; JSON can override them if needed
            options.setPlatformName("Android");        // from BaseOptions
            options.setAutomationName("UiAutomator2"); // UiAutomator2Options default, but explicit is fine


            if (selectedCaps.containsKey("deviceName")) options.setDeviceName(selectedCaps.get("deviceName").toString());
            if (selectedCaps.containsKey("platformVersion")) options.setPlatformVersion(selectedCaps.get("platformVersion").toString());
            if (selectedCaps.containsKey("app")) {
                String appPath = selectedCaps.get("app").toString();
                if (appPath != null && !appPath.isBlank()) {
                    options.setApp(appPath);
                }
            }
            if (selectedCaps.containsKey("appPackage")) options.setAppPackage(selectedCaps.get("appPackage").toString());
            if (selectedCaps.containsKey("appActivity")) options.setAppActivity(selectedCaps.get("appActivity").toString());
            if (selectedCaps.containsKey("udid")) options.setUdid(selectedCaps.get("udid").toString());
            if (selectedCaps.containsKey("autoGrantPermissions")) options.setAutoGrantPermissions(Boolean.parseBoolean(selectedCaps.get("autoGrantPermissions").toString()));
            if (selectedCaps.containsKey("noReset")) options.setNoReset(Boolean.parseBoolean(selectedCaps.get("noReset").toString()));

            String url = selectedCaps.getOrDefault("serverUrl", "http://127.0.0.1:4723").toString();
            System.out.println("Connecting to Appium server at: " + url);
            System.out.println("Options: " + options);

            driver = new AndroidDriver(new URL(url), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
            System.out.println("AndroidDriver initialized successfully");
        } catch (Exception e) {
            System.err.println("Error details: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Failed to start AndroidDriver: " + e.getMessage(), e);
        }
    }

    public static void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
