# HOW TO RUN APPIUM - Complete Guide

## Prerequisites
Before running Appium, make sure you have:

1. **Node.js LTS** installed
   - Download from: https://nodejs.org/
   - Verify: `node --version` (should be v16+ or v18+)

2. **Android SDK** installed and configured
   - Set `ANDROID_HOME` environment variable
   - Add `%ANDROID_HOME%\platform-tools` to PATH
   - Verify: `adb --version`

3. **Android Emulator or Physical Device**
   - Emulator: Start from Android Studio's AVD Manager
   - Physical Device: Enable USB Debugging

---

## Step 1: Install Appium (One-time setup)

Open Command Prompt or PowerShell and run:

```bash
npm install -g appium
```

Verify installation:
```bash
appium --version
```

---

## Step 2: Install UIAutomator2 Driver (One-time setup)

```bash
appium driver install uiautomator2
```

Or using npm:
```bash
npm install -g @appium/uiautomator2-driver
```

Verify:
```bash
appium driver list
```

---

## Step 3: Start Appium Server

**Open a NEW command prompt or PowerShell window and run:**

```bash
appium
```

**You should see output like:**
```
[Appium] Welcome to Appium v2.x.x
[Appium] Non-default server args: {}
[Appium] Appium REST http interface listener started on 0.0.0.0:4723
```

**IMPORTANT: Keep this terminal window open while running tests!**

---

## Step 4: Start Android Emulator (if using emulator)

**Open ANOTHER new command prompt/PowerShell window:**

### Option A: Using Android Studio
- Open Android Studio → Virtual Device Manager → Click Play button on your AVD

### Option B: Using Command Line
```bash
emulator -avd <your_avd_name>
```

Example:
```bash
emulator -avd Pixel_4_API_30
```

List available AVDs:
```bash
emulator -list-avds
```

---

## Step 5: Verify Setup

**In a NEW command prompt, run the status checker:**

```bash
cd C:\data\appium\ecommerce_appium
javac CheckAppiumStatus.java
java CheckAppiumStatus
```

Expected output:
```
✓ SUCCESS: Appium is running on 127.0.0.1:4723
```

---

## Step 6: Run Your Tests

**In a NEW command prompt, from your project directory:**

```bash
cd C:\data\appium\ecommerce_appium
mvn clean test
```

---

## Terminal Layout (Visual Guide)

You should have **3-4 terminal windows open:**

```
┌─────────────────────────────┐
│ Terminal 1: Appium Server   │
│ $ appium                    │
│ [Appium] Listening on 4723  │
│ (KEEP THIS RUNNING)         │
└─────────────────────────────┘

┌─────────────────────────────┐
│ Terminal 2: Android Emulator│
│ $ emulator -avd Pixel_4     │
│ (KEEP THIS RUNNING)         │
└─────────────────────────────┘

┌─────────────────────────────┐
│ Terminal 3: Run Tests       │
│ $ mvn clean test            │
│ (Tests execute here)        │
└─────────────────────────────┘
```

---

## Troubleshooting

### Port 4723 already in use
```bash
# Find and kill process using port 4723
netstat -ano | findstr 4723
taskkill /PID <PID> /F
```

### "Android home not found"
```bash
# Set ANDROID_HOME (Windows Command Prompt)
setx ANDROID_HOME "C:\Users\<YourUsername>\AppData\Local\Android\Sdk"

# Set ANDROID_HOME (PowerShell)
[Environment]::SetEnvironmentVariable("ANDROID_HOME", "C:\Users\<YourUsername>\AppData\Local\Android\Sdk", "User")
```

### ADB not found
```bash
# Add to PATH (Windows)
setx PATH "%PATH%;%ANDROID_HOME%\platform-tools"
```

### Device not detected
```bash
adb devices  # List connected devices
adb kill-server
adb start-server
```

---

## Quick Start Command Summary

```bash
# Terminal 1 - Start Appium
appium

# Terminal 2 - Start Emulator
emulator -avd Pixel_4_API_30

# Terminal 3 - Run Tests
cd C:\data\appium\ecommerce_appium
mvn clean test
```

---

## Common Issues & Solutions

| Issue | Solution |
|-------|----------|
| "Cannot find appium command" | Install Appium: `npm install -g appium` |
| "Port 4723 already in use" | Close other Appium instances or kill the process |
| "Device not found" | Start emulator or connect physical device with USB debugging |
| "ANDROID_HOME not set" | Set environment variable to your Android SDK path |
| "Tests timeout" | Ensure Appium server is running and device is responsive |


