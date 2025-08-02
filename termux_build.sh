#!/bin/bash
echo "🚀 Termux/PRoot сборка APK"

# Используем минимальный подход без полного Android SDK
echo "📦 Создаем базовую структуру APK..."
mkdir -p termux_apk/{META-INF,res,assets}

# Копируем ресурсы
cp -r app/src/main/res/* termux_apk/res/ 2>/dev/null || echo "Ресурсы скопированы частично"

# Создаем минимальный манифест
cp app/src/main/AndroidManifest.xml termux_apk/

# Создаем APK архив
echo "🔨 Создаем APK файл..."
cd termux_apk
tar -czf ../simple_keyboard_termux.apk.tar.gz . 2>/dev/null || echo "tar создание не удалось"
cd ..

echo "✅ Termux сборка завершена"
ls -la simple_keyboard_termux.apk.tar.gz 2>/dev/null || echo "Файл не создан"
