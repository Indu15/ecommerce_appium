# How to Install Target App (com.example.shop) on Android Emulator

## Prerequisites
Before installing, make sure you have:
- ✅ Android emulator running
- ✅ APK file built (app-debug.apk)
- ✅ Android SDK platform-tools installed
- ✅ `adb` command available in terminal

---

## Step 1: Verify Emulator is Running

### Using Command Line
```bash
adb devices
```

**Expected output:**
```
List of attached devices
emulator-5554           device
```

If you see `emulator-5554 device`, your emulator is running ✓

### Using Android Studio
- Open Android Studio Device Manager
- You should see your emulator in the list with a green circle icon

---

## Step 2: Locate Your APK File

The APK file location depends on where you built it:

### If you built the app in Android Studio:
```
C:\Users\YourName\AndroidStudioProjects\E-Commerce-Shop\app\build\outputs\apk\debug\app-debug.apk
```

### If you built using command line:
```
<your_project_root>\app\build\outputs\apk\debug\app-debug.apk
```

**Find the full path:**
```bash
# In your Android project directory
dir app\build\outputs\apk\debug\
```

---

## Step 3: Install APK on Emulator

### Method 1: Using ADB (Command Line) - RECOMMENDED

**Option A: Simple Install**
```bash
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

**Option B: With Full Path**
```bash
adb install -r "C:\Users\YourName\AndroidStudioProjects\E-Commerce-Shop\app\build\outputs\apk\debug\app-debug.apk"
```

**Expected output:**
```
Installing 'app-debug.apk'...
Success
```

### Method 2: Using Android Studio GUI

1. **Open Android Studio**
2. **Build → Build Bundle(s)/APK(s) → Analyze APK**
3. **Select the APK file**
4. **Right-click → Run**

### Method 3: Drag and Drop (Emulator)

1. **Drag the APK file into the running emulator window**
2. **Wait for installation to complete**

---

## Step 4: Verify Installation

**Check if app is installed:**
```bash
adb shell pm list packages | findstr com.example.shop
```

**Expected output:**
```
com.example.shop
```

If you see this, the app is installed ✓

---

## Step 5: Launch the App

### Option A: From Command Line
```bash
adb shell am start -n com.example.shop/.MainActivity
```

### Option B: From Emulator
1. Look for app icon in emulator home screen
2. Tap to launch

### Option C: Verify it Appears
```bash
adb shell cmd package list packages | grep com.example.shop
```

---

## Complete Installation Script

Here's a one-command installation:

```bash
# Build, install, and verify
gradlew.bat assembleDebug && adb install -r app\build\outputs\apk\debug\app-debug.apk && adb shell pm list packages | findstr com.example.shop
```

If you see `com.example.shop` in the output, installation succeeded! ✓

---

## Troubleshooting

### ❌ "adb: command not found"
**Solution**: Add Android SDK platform-tools to PATH
```bash
# Set ANDROID_HOME (Windows Command Prompt)
setx PATH "%PATH%;%ANDROID_HOME%\platform-tools"

# Verify
adb --version
```

### ❌ "No devices found"
**Solution**: Make sure emulator is running
```bash
# Check devices
adb devices

# If no devices, start emulator
emulator -avd Pixel_4_API_31
```

### ❌ "Failure [INSTALL_FAILED_INVALID_APK]"
**Solution**: APK file is corrupted or path is wrong
```bash
# Rebuild APK
gradlew.bat clean assembleDebug

# Then reinstall
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

### ❌ "Failure [INSTALL_FAILED_APP_INCOMPATIBLE]"
**Solution**: Android version mismatch
- Check emulator API level
- Make sure minSdk in build.gradle is ≤ emulator API level

### ❌ "app-debug.apk not found"
**Solution**: APK wasn't built yet
```bash
# Build first
gradlew.bat assembleDebug

# Then install
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

### ❌ "Failure [INSTALL_FAILED_VERSION_DOWNGRADE]"
**Solution**: Older version already installed
```bash
# Uninstall first
adb uninstall com.example.shop

# Then install
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

---

## Quick Reference Commands

```bash
# ===== BUILD =====
gradlew.bat assembleDebug                  # Build APK

# ===== CHECK =====
adb devices                                # List connected devices
adb shell pm list packages | findstr com.example.shop  # Check if installed

# ===== INSTALL =====
adb install -r app\build\outputs\apk\debug\app-debug.apk   # Install APK
adb install app\build\outputs\apk\debug\app-debug.apk       # First install (no force)

# ===== UNINSTALL =====
adb uninstall com.example.shop             # Uninstall app

# ===== LAUNCH =====
adb shell am start -n com.example.shop/.MainActivity    # Launch app

# ===== CLEAR =====
adb shell pm clear com.example.shop        # Clear app data
```

---

## Step-by-Step Example

Here's a complete example from start to finish:

```bash
# Step 1: Navigate to your Android project
cd C:\Users\YourName\AndroidStudioProjects\E-Commerce-Shop

# Step 2: Build the APK
gradlew.bat assembleDebug
# Wait for: BUILD SUCCESSFUL

# Step 3: Check emulator is running
adb devices
# Should see: emulator-5554 device

# Step 4: Install on emulator
adb install -r app\build\outputs\apk\debug\app-debug.apk
# Should see: Success

# Step 5: Verify installation
adb shell pm list packages | findstr com.example.shop
# Should see: com.example.shop

# Step 6: Launch app to verify it works
adb shell am start -n com.example.shop/.MainActivity

# Step 7: You're done! 🎉
```

---

## For Your Appium Tests

Once installed, update your `android.json`:

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

Note: `"noReset": true` keeps the app installed between test runs.

Then run:
```bash
mvn clean test
```

---

## Summary

**3 Simple Commands:**
```bash
# 1. Build
gradlew.bat assembleDebug

# 2. Install
adb install -r app\build\outputs\apk\debug\app-debug.apk

# 3. Verify
adb shell pm list packages | findstr com.example.shop
```

That's it! Your app is now installed on the emulator. ✓


