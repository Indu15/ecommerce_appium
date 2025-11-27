@echo off
REM Quick build script for com.example.shop Android app
REM This script assumes you have Android Studio CLI tools installed

echo ================================================
echo Building com.example.shop Android App
echo ================================================

REM Check if gradlew exists
if not exist "gradlew.bat" (
    echo ERROR: gradlew.bat not found!
    echo Make sure you're in the Android project root directory
    echo and the project was created with Android Studio
    exit /b 1
)

REM Clean previous builds
echo.
echo [1/3] Cleaning previous builds...
call gradlew.bat clean
if errorlevel 1 (
    echo ERROR: Clean failed
    exit /b 1
)

REM Build debug APK
echo.
echo [2/3] Building debug APK...
call gradlew.bat assembleDebug
if errorlevel 1 (
    echo ERROR: Build failed
    exit /b 1
)

REM Show build output location
echo.
echo [3/3] Build complete!
echo.
echo ================================================
echo APK Location:
echo app\build\outputs\apk\debug\app-debug.apk
echo ================================================
echo.
echo Next steps:
echo 1. Install on emulator: adb install -r app\build\outputs\apk\debug\app-debug.apk
echo 2. Verify: adb shell pm list packages ^| grep com.example.shop
echo 3. Update android.json with APK path
echo 4. Run Appium tests: mvn clean test
echo.

pause

