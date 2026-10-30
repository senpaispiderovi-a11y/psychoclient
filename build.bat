@echo off
setlocal
where gradle >nul 2>nul
if errorlevel 1 (
  echo Gradle was not found. Install Gradle 8.14 or newer and add its bin folder to PATH.
  echo Java 21 is also required.
  pause
  exit /b 1
)
gradle build
if errorlevel 1 (
  echo Build failed. Copy the full error output when asking for help.
  pause
  exit /b 1
)
echo.
echo Build successful. Check build\libs for psycho-client-0.1.0.jar.
pause
