
# Appium + Java E-Commerce Test Framework (POM + TestNG + Allure)

## Prereqs
- JDK 17+
- Node.js LTS
- Appium Server + UIAutomator2 driver:
  ```bash
  npm i -g appium @appium/uiautomator2-driver
  appium driver install uiautomator2
  ```
- ANDROID_HOME set, `platform-tools` on PATH
- Real device with USB debugging OR AVD

## Configure
1. Update `src/test/resources/capabilities/android.json`:
   - Set `app` (path to APK) **OR** `appPackage` + `appActivity` if app is preinstalled.
2. Replace placeholder accessibility ids/selectors in `pages/*` to match your app.
3. Start Appium:
   ```bash
   appium
   ```

## Run
```bash
mvn clean test
```

## Allure Report
```bash
# generate & open
allure generate --clean ./allure-results -o ./allure-report
allure open ./allure-report
```
