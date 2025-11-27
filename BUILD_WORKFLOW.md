# Visual Build & Test Workflow

## High-Level Workflow

```
┌─────────────────────────────────────────────────────────┐
│ 1. CREATE ANDROID PROJECT                               │
│    - Package: com.example.shop                          │
│    - Activity: MainActivity                             │
│    - Min SDK: API 31                                    │
└──────────────────────┬──────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────┐
│ 2. ADD CODE FILES                                       │
│    - MainActivity.java                                  │
│    - activity_main.xml layout                          │
│    - Update build.gradle (applicationId)               │
└──────────────────────┬──────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────┐
│ 3. BUILD APK                                            │
│    - Build → Build APK(s) in Android Studio            │
│    - OR: gradlew.bat assembleDebug                     │
│    - Output: app-debug.apk                             │
└──────────────────────┬──────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────┐
│ 4. INSTALL ON EMULATOR                                  │
│    - Start Android Emulator (API 16)                    │
│    - adb install -r app-debug.apk                      │
│    - Verify: adb shell pm list packages                │
└──────────────────────┬──────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────┐
│ 5. UPDATE TEST CONFIG (android.json)                    │
│    - Set correct APK path OR noReset: true             │
│    - Verify appPackage: com.example.shop               │
│    - Verify platformVersion: 16                        │
└──────────────────────┬──────────────────────────────────┘
                       │
                       ▼
┌─────────────────────────────────────────────────────────┐
│ 6. RUN APPIUM & TESTS                                   │
│    - Terminal 1: appium                                │
│    - Terminal 2: emulator running                      │
│    - Terminal 3: mvn clean test                        │
└─────────────────────────────────────────────────────────┘
```

## Detailed Steps

### Step 1: Create Android Project

```
Android Studio
    ↓
File → New → New Project
    ↓
Select: Empty Views Activity
    ↓
Configure:
  - Name: E-Commerce Shop
  - Package: com.example.shop  ✓
  - Language: Java             ✓
  - Min SDK: API 31            ✓
    ↓
Finish → Wait for sync
```

### Step 2: Add Code Files

```
Your Android Project Root
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/example/shop/
│   │   │   │   └── MainActivity.java          ← REPLACE with SAMPLE_MainActivity.java
│   │   │   ├── res/layout/
│   │   │   │   └── activity_main.xml          ← REPLACE with SAMPLE_activity_main.xml
│   │   │   └── AndroidManifest.xml
│   │   └── test/
│   ├── build.gradle                           ← UPDATE applicationId
│   └── ...
├── gradlew.bat
└── ...
```

### Step 3: Build APK

```
Option A (GUI):
  Build → Build APK(s) → Wait → Success!

Option B (Command Line):
  gradlew.bat assembleDebug

Option C (Script):
  BUILD_APP.bat  OR  .\BUILD_APP.ps1

Result:
  app/build/outputs/apk/debug/app-debug.apk
```

### Step 4: Install on Emulator

```
Prerequisites:
  ✓ Emulator is running (Android 16)
  ✓ APK built successfully

Commands:
  1. adb install -r app/build/outputs/apk/debug/app-debug.apk
  2. adb shell pm list packages | findstr com.example.shop
     (Should see: com.example.shop)
```

### Step 5: Update Test Config

File: `android.json`

```json
{
  "appPackage": "com.example.shop",      ← Must match
  "appActivity": ".MainActivity",         ← Must match
  "platformVersion": "16",                ← Must match emulator
  "app": "C:/path/to/app-debug.apk",    ← Full path to APK
  "noReset": false                        ← or true if pre-installed
}
```

### Step 6: Run Tests

```
Terminal 1 (Keep running):
  $ appium
  [Appium] Listening on 0.0.0.0:4723

Terminal 2 (Keep running):
  Android Emulator (started from Android Studio or command)

Terminal 3 (Run commands):
  $ cd C:\data\appium\ecommerce_appium
  $ mvn clean test
  
  Expected output:
  [INFO] BUILD SUCCESS
```

---

## Quick Reference Commands

### Build Commands
```bash
# Full build
gradlew.bat clean assembleDebug

# Just build
gradlew.bat assembleDebug

# Using script
BUILD_APP.bat
```

### Install Commands
```bash
# Install on running emulator
adb install -r app/build/outputs/apk/debug/app-debug.apk

# Uninstall
adb uninstall com.example.shop

# List installed packages
adb shell pm list packages | findstr com.example.shop
```

### Emulator Commands
```bash
# List available emulators
emulator -list-avds

# Start emulator
emulator -avd Pixel_4_API_31

# List running emulators
adb devices
```

### Test Commands
```bash
# Run all tests
cd C:\data\appium\ecommerce_appium
mvn clean test

# Run specific test class
mvn test -Dtest=CheckoutFlowTest
```

---

## Checklist for Success

- [ ] Android Studio installed
- [ ] Android SDK (API 31+) installed
- [ ] Android project created with package `com.example.shop`
- [ ] Code files replaced (MainActivity.java, activity_main.xml)
- [ ] build.gradle updated with correct applicationId
- [ ] APK built successfully
- [ ] Emulator running with Android 16
- [ ] APK installed on emulator
- [ ] android.json configured with correct paths
- [ ] Appium server running on 4723
- [ ] mvn clean test runs successfully


