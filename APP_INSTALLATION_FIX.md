# How to Fix: App Installation Issue

## Problem
```
Original error: Either provide 'app' option to install 'com.example.shop' 
or consider setting 'noReset' to 'true' if 'com.example.shop' is 
supposed to be preinstalled.
```

Appium couldn't find the app to test because:
1. No APK file path was provided in the `app` field
2. The app `com.example.shop` was not pre-installed on the emulator

## Solution Implemented

### 1. Updated `android.json`
Added `"noReset": true` to tell Appium that the app is pre-installed:

```json
{
  "serverUrl": "http://127.0.0.1:4723",
  "deviceName": "Android Emulator",
  "platformVersion": "16",
  "app": "",
  "appPackage": "com.example.shop",
  "appActivity": ".MainActivity",
  "autoGrantPermissions": true,
  "noReset": true
}
```

### 2. Updated `DriverManager.java`
Added code to pass the `noReset` option to Appium:

```java
if (caps.containsKey("noReset")) options.setNoReset(Boolean.parseBoolean(caps.get("noReset").toString()));
```

## What You Need To Do Now

### Option 1: Pre-install the App (Recommended for existing app)
If `com.example.shop` is a real app that should already be installed on your emulator:

1. Make sure the app is installed on your emulator
2. Run tests as normal:
   ```bash
   mvn clean test
   ```

### Option 2: Provide an APK File (Recommended for new app)
If you have an APK file for `com.example.shop`:

1. Update `android.json` with the path to your APK:
   ```json
   {
     "app": "C:/path/to/your/app.apk",
     "noReset": false
   }
   ```

2. Run tests:
   ```bash
   mvn clean test
   ```

## Configuration Options

| Option | Purpose | Value |
|--------|---------|-------|
| `app` | Path to APK file to install | File path or empty string |
| `noReset` | Keep app data between sessions | `true` or `false` |
| `appPackage` | Package name of the app | `com.example.shop` |
| `appActivity` | Main activity to launch | `.MainActivity` |

## Next Steps

1. Verify `com.example.shop` is installed on your emulator
2. Ensure Appium server is running: `appium`
3. Ensure your Android emulator is running
4. Run tests:
   ```bash
   mvn clean test
   ```

If the app is not installed, you'll need to either:
- Install it manually via APK
- Or provide the APK path in `android.json`

