# Complete Guide: Build & Test com.example.shop App

## Quick Summary

You have:
- ✅ Appium test framework (already set up)
- ✅ Test configurations
- ❌ Android app to test (`com.example.shop`)

You need to:
1. Create an Android app project
2. Build it as an APK
3. Install on emulator
4. Run your Appium tests against it

---

## Step-by-Step Instructions

### Step 1: Create Android Project in Android Studio

**1.1 Open Android Studio**
- Launch Android Studio

**1.2 Create New Project**
- Click: **File → New → New Project**
- Select: **Empty Views Activity**
- Click: **Next**

**1.3 Configure Project**
| Setting | Value |
|---------|-------|
| Name | `E-Commerce Shop` or any name |
| Package name | **`com.example.shop`** ← CRITICAL |
| Save location | Choose any folder |
| Language | **Java** |
| Minimum SDK | **API 31 (Android 12)** |

**1.4 Click "Finish"**
- Wait for Android Studio to create and sync the project (~2-5 minutes)

---

### Step 2: Replace Code Files

**2.1 Replace MainActivity.java**

Location: `app/src/main/java/com/example/shop/MainActivity.java`

Copy content from: `SAMPLE_MainActivity.java` (in this directory)

**2.2 Replace activity_main.xml**

Location: `app/src/main/res/layout/activity_main.xml`

Copy content from: `SAMPLE_activity_main.xml` (in this directory)

**2.3 Update build.gradle**

Location: `app/build.gradle`

Make sure these settings exist:
```gradle
android {
    compileSdk 33

    defaultConfig {
        applicationId "com.example.shop"
        minSdk 31
        targetSdk 33
        versionCode 1
        versionName "1.0"
    }
}

dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
}
```

---

### Step 3: Build the App

Choose ONE method:

#### Method A: Using Android Studio GUI (Easiest)

1. Click: **Build → Build Bundle(s)/APK(s) → Build APK(s)**
2. Wait for "Build Successful" message
3. APK created at: `app/build/outputs/apk/debug/app-debug.apk`

#### Method B: Using Command Line

Open Terminal/PowerShell in your Android project root:

```bash
# For Command Prompt or PowerShell:
gradlew.bat assembleDebug

# Output APK location:
# app\build\outputs\apk\debug\app-debug.apk
```

#### Method C: Using Build Script (Windows Only)

In your Android project directory, run:
```bash
# Using batch file
BUILD_APP.bat

# Or using PowerShell
.\BUILD_APP.ps1
```

---

### Step 4: Install on Emulator

**4.1 Make sure emulator is running**
```bash
emulator -avd <your_avd_name>
# or start from Android Studio's Device Manager
```

**4.2 Install the APK**
```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

**4.3 Verify installation**
```bash
adb shell pm list packages | findstr com.example.shop
# Should show: com.example.shop
```

---

### Step 5: Update Appium Test Configuration

Edit: `C:\data\appium\ecommerce_appium\src\test\resources\capabilities\android.json`

**Option A: Using APK file path (Fresh install each time)**
```json
{
  "serverUrl": "http://127.0.0.1:4723",
  "deviceName": "Android Emulator",
  "platformVersion": "16",
  "app": "C:/path/to/your/app/build/outputs/apk/debug/app-debug.apk",
  "appPackage": "com.example.shop",
  "appActivity": ".MainActivity",
  "autoGrantPermissions": true,
  "noReset": false
}
```

Replace `C:/path/to/your/app/build/outputs/apk/debug/app-debug.apk` with actual path!

Example on Windows:
```json
"app": "C:/Users/YourName/AndroidStudioProjects/E-Commerce-Shop/app/build/outputs/apk/debug/app-debug.apk"
```

**Option B: Using noReset (Keep app installed)**
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

---

### Step 6: Run Tests

**Make sure these are running:**

1. **Appium Server** (in Terminal 1)
   ```bash
   appium
   ```

2. **Android Emulator** (in Terminal 2 or Android Studio)
   ```bash
   emulator -avd <your_avd_name>
   ```

3. **Run Tests** (in Terminal 3)
   ```bash
   cd C:\data\appium\ecommerce_appium
   mvn clean test
   ```

---

## Troubleshooting

### APK Installation Fails
```bash
# Clear app data first
adb shell pm clear com.example.shop

# Try reinstall
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### App Not Found on Emulator
```bash
# List all installed packages
adb shell pm list packages

# Uninstall and reinstall
adb uninstall com.example.shop
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

### Tests Can't Connect to App
1. Verify app is installed: `adb shell pm list packages | findstr com.example.shop`
2. Verify Appium is running on `http://127.0.0.1:4723`
3. Check `android.json` has correct `appPackage` and `appActivity`

### Build Fails in Android Studio
```bash
# Clean and rebuild
gradlew.bat clean
gradlew.bat assembleDebug
```

### Emulator Not Starting
- Use Android Studio's Device Manager
- Or check: `emulator -list-avds` to see available emulators

---

## File Reference

| File | Purpose |
|------|---------|
| `CREATE_ANDROID_APP.md` | Detailed Android app creation guide |
| `SAMPLE_MainActivity.java` | Sample Java code for MainActivity |
| `SAMPLE_activity_main.xml` | Sample XML layout file |
| `BUILD_APP.bat` | Automatic build script (Windows) |
| `BUILD_APP.ps1` | Automatic build script (PowerShell) |

---

## Next Time You Need to Rebuild

After the initial setup, to update and rebuild the app:

```bash
# In Android project directory
gradlew.bat clean assembleDebug

# Then install on emulator
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Run your Appium tests
cd C:\data\appium\ecommerce_appium
mvn clean test
```

---

## Did You Know?

- The package name `com.example.shop` is what Appium uses to find your app
- The activity name `.MainActivity` is where Appium starts your app
- The APK is like an executable file for Android apps
- You only need to build the APK once, then you can use `noReset: true` to skip reinstalls


