#!/bin/bash

echo "🚀 Simple Keyboard - Быстрая сборка APK"
echo "======================================"

# Проверяем наличие Android SDK
if [ -n "$ANDROID_HOME" ] || [ -n "$ANDROID_SDK_ROOT" ]; then
    echo "✅ Android SDK найден"
    echo "📦 Сборка APK..."
    chmod +x gradlew
    ./gradlew assembleDebug
    
    if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
        echo "✅ APK успешно собран!"
        echo "📍 Путь: $(pwd)/app/build/outputs/apk/debug/app-debug.apk"
        echo "📊 Размер: $(du -h app/build/outputs/apk/debug/app-debug.apk | cut -f1)"
    else
        echo "❌ Ошибка сборки APK"
    fi
else
    echo "❌ Android SDK не найден"
    echo ""
    echo "🔧 Альтернативные способы сборки:"
    echo "1. 🏢 Android Studio - откройте проект и соберите через Build menu"
    echo "2. 🚀 GitHub Actions - загрузите код на GitHub"
    echo "3. 🐳 Docker - запустите: ./docker-build.sh"
    echo ""
    echo "📖 Подробные инструкции в BUILD_INSTRUCTIONS.md"
fi