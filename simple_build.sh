#!/bin/bash
echo "🔨 Простая сборка APK без Gradle..."

# Создаем директории
mkdir -p build/classes build/res build/apk

# Компилируем ресурсы
echo "📦 Обрабатываем ресурсы..."
aapt package -f -m \
  -S app/src/main/res \
  -M app/src/main/AndroidManifest.xml \
  -J build/gen \
  -F build/resources.ap_ 2>/dev/null || echo "Ошибка: нужен android.jar"

echo "✅ Скрипт выполнен"
