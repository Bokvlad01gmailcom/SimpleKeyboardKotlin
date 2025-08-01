#!/bin/sh

# Simple Gradle wrapper
GRADLE_VERSION=8.2
GRADLE_HOME="$HOME/.gradle/wrapper/dists/gradle-$GRADLE_VERSION"

if [ ! -d "$GRADLE_HOME" ]; then
    echo "Downloading Gradle $GRADLE_VERSION..."
    mkdir -p "$GRADLE_HOME"
    cd "$GRADLE_HOME"
    wget -q "https://services.gradle.org/distributions/gradle-$GRADLE_VERSION-bin.zip"
    unzip -q "gradle-$GRADLE_VERSION-bin.zip"
    cd -
fi

exec "$GRADLE_HOME/gradle-$GRADLE_VERSION/bin/gradle" "$@"