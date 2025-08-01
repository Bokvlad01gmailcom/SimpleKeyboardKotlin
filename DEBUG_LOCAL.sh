#!/bin/bash

echo "🔍 LOCAL DIAGNOSTIC SCRIPT"
echo "=========================="

echo "1. Project Structure:"
ls -la

echo -e "\n2. Gradle Files:"
ls -la *.gradle* app/*.gradle* 2>/dev/null || echo "Some gradle files missing"

echo -e "\n3. Source Files:"
find app/src -name "*.kt" 2>/dev/null | head -5 || echo "No Kotlin source files found"

echo -e "\n4. Gradle Wrapper:"
ls -la gradlew gradle/wrapper/ 2>/dev/null || echo "Gradle wrapper issues"

echo -e "\n5. Java Version:"
java -version 2>&1 || echo "Java not found"

echo -e "\n6. Gradle Version Test:"
chmod +x gradlew 2>/dev/null
./gradlew --version 2>&1 || echo "Gradle wrapper failed"

echo -e "\n7. Project Validation:"
./gradlew projects 2>&1 || echo "Project validation failed"

echo -e "\n8. Dependencies Check:"
./gradlew dependencies --configuration debugCompileClasspath 2>&1 | head -20 || echo "Dependencies check failed"

echo -e "\n9. Simple Build Test:"
export GRADLE_OPTS="-Xmx512m"
./gradlew clean 2>&1 || echo "Clean failed"

echo -e "\n10. Build Attempt:"
./gradlew assembleDebug --no-daemon --stacktrace 2>&1 | tail -50 || echo "Build failed"

echo -e "\n=== DIAGNOSTIC COMPLETE ==="