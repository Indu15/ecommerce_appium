
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

    public static void startAndroid(String capsJsonPath) {
        try {
            Map<String, Object> caps = Config.loadCaps(capsJsonPath);
            UiAutomator2Options options = new UiAutomator2Options();

            if (caps.containsKey("deviceName")) options.setDeviceName(caps.get("deviceName").toString());
            if (caps.containsKey("platformVersion")) options.setPlatformVersion(caps.get("platformVersion").toString());
            if (caps.containsKey("app")) {
                String appPath = caps.get("app").toString();
                if (appPath != null && !appPath.isBlank()) {
                    options.setApp(appPath);
                }
            }
            if (caps.containsKey("appPackage")) options.setAppPackage(caps.get("appPackage").toString());
            if (caps.containsKey("appActivity")) options.setAppActivity(caps.get("appActivity").toString());
            if (caps.containsKey("udid")) options.setUdid(caps.get("udid").toString());
            if (caps.containsKey("autoGrantPermissions")) options.setAutoGrantPermissions(Boolean.parseBoolean(caps.get("autoGrantPermissions").toString()));

            String url = caps.getOrDefault("serverUrl", "http://127.0.0.1:4723").toString();
            driver = new AndroidDriver(new URL(url), options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        } catch (Exception e) {
            throw new RuntimeException("Failed to start AndroidDriver", e);
        }
    }

    public static void stop() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }
}
