#!/bin/bash

echo "🔧 FIXING BUILD ISSUES"
echo "======================"

# Clear all conflicting environment variables
unset GRADLE_OPTS
unset GRADLE_USER_HOME
unset JAVA_OPTS
unset _JAVA_OPTIONS

# Set clean environment
export JAVA_HOME="/usr/lib/jvm/java-17-openjdk-arm64"
export PATH="$JAVA_HOME/bin:$PATH"
export GRADLE_OPTS="-Xmx1024m -Dfile.encoding=UTF-8"

echo "1. Environment cleaned and set:"
echo "JAVA_HOME: $JAVA_HOME"
echo "GRADLE_OPTS: $GRADLE_OPTS"

echo -e "\n2. Testing Java:"
java -version

echo -e "\n3. Testing Gradle wrapper:"
chmod +x gradlew
./gradlew --version

echo -e "\n4. Testing project:"
./gradlew projects

echo -e "\n5. Attempting clean build:"
./gradlew clean assembleDebug --no-daemon --stacktrace