#!/bin/bash

echo "🔄 REBUILDING GRADLE WRAPPER"
echo "============================="

# Clean environment
unset GRADLE_OPTS
unset GRADLE_USER_HOME
unset JAVA_OPTS
unset _JAVA_OPTIONS

# Download and setup Gradle manually
echo "1. Downloading Gradle 8.2..."
cd /tmp
wget -q https://services.gradle.org/distributions/gradle-8.2-bin.zip
unzip -q gradle-8.2-bin.zip

echo "2. Setting up clean Gradle..."
export GRADLE_HOME=/tmp/gradle-8.2
export PATH=$GRADLE_HOME/bin:$PATH

echo "3. Creating new wrapper..."
cd - 
$GRADLE_HOME/bin/gradle wrapper --gradle-version 8.2

echo "4. Testing new wrapper..."
chmod +x gradlew
./gradlew --version

echo "5. Testing build..."
export GRADLE_OPTS="-Xmx1024m -Dfile.encoding=UTF-8"
./gradlew clean assembleDebug --no-daemon