# Quick build script for com.example.shop Android app (PowerShell)
# This script assumes you have Android Studio CLI tools installed

Write-Host "================================================" -ForegroundColor Cyan
Write-Host "Building com.example.shop Android App" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Cyan

# Check if gradlew exists
if (-not (Test-Path "gradlew.bat")) {
    Write-Host "ERROR: gradlew.bat not found!" -ForegroundColor Red
    Write-Host "Make sure you're in the Android project root directory" -ForegroundColor Red
    exit 1
}

# Clean previous builds
Write-Host "`n[1/3] Cleaning previous builds..." -ForegroundColor Yellow
& .\gradlew.bat clean
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Clean failed" -ForegroundColor Red
    exit 1
}

# Build debug APK
Write-Host "`n[2/3] Building debug APK..." -ForegroundColor Yellow
& .\gradlew.bat assembleDebug
if ($LASTEXITCODE -ne 0) {
    Write-Host "ERROR: Build failed" -ForegroundColor Red
    exit 1
}

# Show build output location
Write-Host "`n[3/3] Build complete!" -ForegroundColor Green
Write-Host "`n================================================" -ForegroundColor Green
Write-Host "APK Location:" -ForegroundColor Green
Write-Host "app\build\outputs\apk\debug\app-debug.apk" -ForegroundColor Cyan
Write-Host "================================================" -ForegroundColor Green
Write-Host "`nNext steps:" -ForegroundColor Yellow
Write-Host "1. Install on emulator: adb install -r app\build\outputs\apk\debug\app-debug.apk" -ForegroundColor White
Write-Host "2. Verify: adb shell pm list packages | findstr com.example.shop" -ForegroundColor White
Write-Host "3. Update android.json with APK path" -ForegroundColor White
Write-Host "4. Run Appium tests: mvn clean test" -ForegroundColor White
Write-Host ""

Read-Host "Press Enter to exit"

