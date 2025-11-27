# Test Failure - Root Cause & Resolution

## Problem
```
[ERROR] Unable to find an active device or emulator with OS 14. 
The following are available: emulator-5554 (16)
```

## Root Cause
The test configuration file (`android.json`) was set to target **Android API Level 14**, but the running Android emulator was running **Android API Level 16**.

Appium refused to connect because of this mismatch.

## Solution Applied
Updated `src/test/resources/capabilities/android.json`:

**Before:**
```json
{
  "platformVersion": "14",
  ...
}
```

**After:**
```json
{
  "platformVersion": "16",
  ...
}
```

## Next Steps
1. Make sure Appium server is still running: `appium`
2. Make sure your Android emulator is running
3. Run the tests again:
   ```bash
   mvn clean test
   ```

## Important Notes
- The `platformVersion` in `android.json` must match your actual device/emulator's Android version
- You can check your emulator's Android version in Android Studio's Device Manager
- If you use a different emulator or device, update `platformVersion` accordingly
- You can also add `"udid"` to the JSON if you want to target a specific device

## Other Configuration Fields
If future test failures occur related to configuration, check these fields in `android.json`:
- **serverUrl**: Where Appium server is running (default: `http://127.0.0.1:4723`)
- **deviceName**: Human-readable name (doesn't need to match exactly)
- **appPackage**: The Android package of the app to test
- **appActivity**: The main activity to launch
- **udid**: Device serial number (optional, for specific devices)

