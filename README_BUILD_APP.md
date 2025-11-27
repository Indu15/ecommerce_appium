# Summary: How to Build com.example.shop App

## TL;DR (Too Long; Didn't Read)

You need an Android app with package name `com.example.shop` for your Appium tests to run against.

### The 3 Options:

#### Option 1: Create from Scratch (Most Control)
1. Open Android Studio
2. Create new project with package `com.example.shop`
3. Copy code from `SAMPLE_MainActivity.java` and `SAMPLE_activity_main.xml`
4. Build APK: `Build → Build APK(s)`
5. Install: `adb install -r app-debug.apk`
6. Update `android.json` with APK path
7. Run tests: `mvn clean test`

#### Option 2: Use Existing App
If you already have an Android app you want to test:
1. Get its package name (usually on Google Play Store description)
2. Update `android.json` with correct `appPackage` and `appActivity`
3. Set `"noReset": true` if app is pre-installed
4. Run tests: `mvn clean test`

#### Option 3: Use a Test App (If Available)
If you have an APK file already:
1. Copy APK to accessible location
2. Update `android.json` with full path to APK
3. Run tests: `mvn clean test`

---

## What You Have vs What You Need

### What You Have ✅
- Appium testing framework set up
- Test scripts written (CheckoutFlowTest, HomePage, etc.)
- Test configuration (testng.xml, android.json)
- Maven project ready

### What You Need ❌
- An Android app with package name `com.example.shop`
- Either as APK file OR pre-installed on emulator

---

## File Guide

I've created these files to help you:

| File | Use Case |
|------|----------|
| `CREATE_ANDROID_APP.md` | Detailed step-by-step guide to create app from scratch |
| `COMPLETE_BUILD_GUIDE.md` | Comprehensive guide with troubleshooting |
| `BUILD_WORKFLOW.md` | Visual workflow and quick reference |
| `SAMPLE_MainActivity.java` | Copy/paste Java code for your app |
| `SAMPLE_activity_main.xml` | Copy/paste XML layout for your app |
| `BUILD_APP.bat` | Automated build script (Windows batch) |
| `BUILD_APP.ps1` | Automated build script (PowerShell) |

**Start with**: `COMPLETE_BUILD_GUIDE.md` → It's the easiest!

---

## Quick Start Path

### If you want to build app from scratch:

1. Read: `COMPLETE_BUILD_GUIDE.md` (Section: Step-by-Step Instructions)
2. Follow Android Studio setup section
3. Copy code from `SAMPLE_MainActivity.java` and `SAMPLE_activity_main.xml`
4. Run: `BUILD_APP.bat` to build APK
5. Install: `adb install -r app\build\outputs\apk\debug\app-debug.apk`
6. Update: `android.json` with APK path
7. Run tests: `mvn clean test`

**Total time**: 15-20 minutes

---

### If you already have an app:

1. Find package name (e.g., `com.example.shop`)
2. Find main activity name (e.g., `.MainActivity`)
3. Update `android.json`:
   ```json
   {
     "appPackage": "your.actual.package.name",
     "appActivity": ".YourMainActivity",
     "noReset": true
   }
   ```
4. Run tests: `mvn clean test`

**Total time**: 2 minutes

---

## Key Concepts

### Package Name
- Unique identifier for an Android app
- Format: `com.company.appname`
- Example: `com.example.shop`, `com.instagram.android`, `com.amazon.mShop`
- Appium uses this to find your app

### Activity Name
- The screen/window that launches when app starts
- Usually `.MainActivity` for simple apps
- Appium uses this to know which screen to start testing from

### APK
- Android Package - compressed file containing your app
- Like .exe for Android
- Created by building your app in Android Studio
- Installed on emulator with `adb install`

### Emulator
- Virtual Android device running on your computer
- Simulates a real phone
- You can start multiple emulators
- Tests run against apps installed on emulator

---

## Testing Flow

```
Your Computer
├── Android Studio
│   └── Your App Source Code
│       └── Build → APK file
├── Emulator (Virtual Android Device)
│   └── Installs APK
│       └── App running: com.example.shop
├── Appium Server
│   └── Listens on port 4723
├── Your Tests (mvn test)
│   └── Connect to Appium
│       └── Appium controls emulator
│           └── Automates app on emulator
```

---

## Common Mistakes to Avoid

❌ **Wrong package name** → Tests can't find app
❌ **APK path incorrect** → Build error
❌ **Emulator not running** → Connection timeout
❌ **Appium not running** → Connection refused
❌ **Platform version mismatch** → App not found (already fixed!)

---

## Next Steps

Choose based on your situation:

### 👉 I want to CREATE a new app
→ Read: `COMPLETE_BUILD_GUIDE.md`

### 👉 I already HAVE an app
→ Update `android.json` with correct package name
→ Run: `mvn clean test`

### 👉 I'm STUCK
→ Read: `COMPLETE_BUILD_GUIDE.md` → Section: Troubleshooting

---

## Support Files Location

All guide files are in:
```
C:\data\appium\ecommerce_appium\
├── CREATE_ANDROID_APP.md
├── COMPLETE_BUILD_GUIDE.md
├── BUILD_WORKFLOW.md
├── SAMPLE_MainActivity.java
├── SAMPLE_activity_main.xml
├── BUILD_APP.bat
├── BUILD_APP.ps1
└── (this file)
```

---

## Questions?

**Q: Do I have to build the app or can I use an existing one?**
A: Either! If you have an existing Android app, just update android.json with its package name.

**Q: What if I don't have Android Studio?**
A: You need it to create/build Android apps. Download from: https://developer.android.com/studio

**Q: Can I use a different app?**
A: Yes! Any Android app works. Just update android.json with correct package name and activity.

**Q: Do I need to rebuild the APK every time?**
A: No! Build once, install once, then use `noReset: true` to keep it installed.

**Q: How long does build take?**
A: First build: 5-10 minutes (downloads dependencies)
   Subsequent builds: 30 seconds - 2 minutes


