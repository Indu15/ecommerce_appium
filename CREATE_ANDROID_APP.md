# How to Create & Build Android App: com.example.shop

## Overview
This guide will help you create a basic Android e-commerce app that your Appium tests can automate.

## Prerequisites
- Android Studio installed
- Android SDK (API Level 31+)
- Java Development Kit (JDK 11+)

## Step 1: Create a New Android Project

### Option A: Using Android Studio (GUI - Easiest)

1. **Open Android Studio**
2. **File → New → New Project**
3. **Select "Empty Views Activity"**
4. **Configure the project:**
   - Name: `E-Commerce Shop`
   - Package name: `com.example.shop` ← **IMPORTANT**
   - Save location: Choose a location
   - Language: Java
   - Minimum SDK: API 31 (Android 12)

5. **Click "Finish"** and wait for the project to build

### Option B: Using Command Line

```bash
# Install Android Studio CLI tools
# Then create project via command line:

mkdir -p ~/AndroidProjects
cd ~/AndroidProjects

# Create basic app structure
mkdir -p com.example.shop/app/src/main/java/com/example/shop
mkdir -p com.example.shop/app/src/main/res/layout
mkdir -p com.example.shop/app/src/main/res/values
```

## Step 2: Update AndroidManifest.xml

Replace the content with:

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.shop">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:supportsRtl="true"
        android:theme="@style/Theme.ECommerceShop">

        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

    </application>

</manifest>
```

## Step 3: Create MainActivity.java

Path: `app/src/main/java/com/example/shop/MainActivity.java`

```java
package com.example.shop;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText searchInput;
    private Button searchButton;
    private ListView productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Find views by ID
        searchInput = findViewById(R.id.search_input);
        searchButton = findViewById(R.id.search_button);
        productList = findViewById(R.id.product_list);

        // Set click listener
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchInput.getText().toString();
                Toast.makeText(MainActivity.this, "Searching for: " + query, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
```

## Step 4: Create activity_main.xml

Path: `app/src/main/res/layout/activity_main.xml`

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <EditText
        android:id="@+id/search_input"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:hint="Search products"
        android:inputType="text" />

    <Button
        android:id="@+id/search_button"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Search" />

    <ListView
        android:id="@+id/product_list"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</LinearLayout>
```

## Step 5: Update build.gradle

Path: `app/build.gradle`

Make sure `applicationId` matches your package name:

```gradle
android {
    compileSdk 33

    defaultConfig {
        applicationId "com.example.shop"  // ← IMPORTANT
        minSdk 31
        targetSdk 33
        versionCode 1
        versionName "1.0"
    }

    buildTypes {
        release {
            minifyEnabled false
        }
    }

    compileOptions {
        sourceCompatibility JavaVersion.VERSION_11
        targetCompatibility JavaVersion.VERSION_11
    }
}

dependencies {
    implementation 'androidx.appcompat:appcompat:1.6.1'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
}
```

## Step 6: Build the APK

### Option A: Using Android Studio

1. **Build → Build Bundle(s)/APK(s) → Build APK(s)**
2. Wait for build to complete
3. APK location: `app/build/outputs/apk/debug/app-debug.apk`

### Option B: Using Command Line

```bash
cd your_project_directory
./gradlew build
# or for debug APK only:
./gradlew assembleDebug
```

Output APK: `app/build/outputs/apk/debug/app-debug.apk`

## Step 7: Install on Emulator

```bash
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

Verify installation:
```bash
adb shell pm list packages | grep com.example.shop
```

## Step 8: Update Your Test Configuration

Update `android.json`:

```json
{
  "serverUrl": "http://127.0.0.1:4723",
  "deviceName": "Android Emulator",
  "platformVersion": "16",
  "app": "C:/path/to/your/app-debug.apk",
  "appPackage": "com.example.shop",
  "appActivity": ".MainActivity",
  "autoGrantPermissions": true,
  "noReset": false
}
```

Or if you prefer to use `noReset` (assuming app is already installed):

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

## Step 9: Run Your Tests

```bash
cd C:\data\appium\ecommerce_appium
mvn clean test
```

## Troubleshooting

### App won't install
```bash
# Clear app data
adb shell pm clear com.example.shop
# Try reinstall
adb install -r app-debug.apk
```

### Can't find app on emulator
```bash
# Check if installed
adb shell pm list packages | grep com.example.shop
# List all packages
adb shell pm list packages
```

### Build fails
```bash
# Clean and rebuild
./gradlew clean build
# Check SDK is installed
sdkmanager --list
```

## Next Steps

1. Create the Android app project following this guide
2. Build the APK
3. Install on your emulator
4. Update `android.json` with correct APK path or use `noReset: true`
5. Run your Appium tests: `mvn clean test`


