# 🔨 Инструкции по сборке APK

## 📱 Способы получения готового APK

### 1. 🚀 GitHub Actions (Рекомендуется - автоматическая сборка)

**Шаги:**
1. Создайте репозиторий на GitHub
2. Загрузите код:
   ```bash
   cd SimpleKeyboardKotlin
   git remote add origin https://github.com/YOUR_USERNAME/SimpleKeyboardKotlin.git
   git push -u origin main
   ```
3. Перейдите в репозиторий → Actions → Build APK
4. Дождитесь завершения сборки (~3-5 минут)
5. Скачайте APK из Artifacts

**Результат:** ✅ Готовый APK без установки Android SDK

### 2. 🏢 Android Studio (Локальная сборка)

**Требования:**
- Android Studio
- Android SDK (автоматически устанавливается)

**Шаги:**
1. Откройте Android Studio
2. File → Open → выберите папку SimpleKeyboardKotlin
3. Дождитесь синхронизации Gradle
4. Build → Build Bundle(s) / APK(s) → Build APK(s)
5. APK в `app/build/outputs/apk/debug/app-debug.apk`

**Результат:** ✅ Готовый APK + возможность отладки

### 3. 🐳 Docker (Если есть Docker)

**Команда:**
```bash
cd SimpleKeyboardKotlin
docker run --rm -v $(pwd):/workspace \
  cimg/android:2023.10 \
  bash -c "cd /workspace && chmod +x gradlew && ./gradlew assembleDebug"
```

**Результат:** ✅ Готовый APK без установки Android SDK

### 4. ⚡ Командная строка (Если есть Android SDK)

**Требования:**
- Android SDK
- ANDROID_HOME переменная окружения

**Команды:**
```bash
cd SimpleKeyboardKotlin
chmod +x gradlew
./gradlew assembleDebug
```

**Результат:** ✅ Готовый APK

## 🎯 Что получите после сборки

**APK файл:** `app-debug.apk` (~500KB)

**Функциональность:**
- ✅ InputMethodService - полная системная клавиатура
- ✅ Плавающая кнопка Enter - работает поверх всех приложений
- ✅ Перетаскивание кнопки по экрану
- ✅ Material Design 3 UI
- ✅ Debug режим с логами

## 📱 Установка APK

1. **Включите "Неизвестные источники"** в настройках Android
2. **Установите APK:** `adb install app-debug.apk` или через файловый менеджер
3. **Откройте приложение** Simple Keyboard
4. **Включите клавиатуру:**
   - Нажмите "Включить клавиатуру в системе"
   - Найдите Simple Keyboard в списке и включите
5. **Покажите плавающую кнопку:**
   - Нажмите "Показать плавающую кнопку"
   - Разрешите "Поверх других приложений"
6. **Готово!** Плавающая кнопка появится на экране

## 🔧 Устранение проблем

### Gradle не работает локально
- ✅ Используйте GitHub Actions
- ✅ Используйте Android Studio
- ✅ Используйте Docker

### APK не устанавливается
- Включите "Неизвестные источники"
- Проверьте версию Android (нужен 5.0+)

### Клавиатура не появляется в настройках
- Переустановите APK
- Перезагрузите устройство

### Плавающая кнопка не работает
- Разрешите "Поверх других приложений"
- Включите клавиатуру в системных настройках

## 📊 Статистика проекта

- **Kotlin файлов:** 7
- **XML файлов:** 10
- **Размер проекта:** 3.1MB
- **Размер APK:** ~500KB
- **Поддержка:** Android 5.0+ (API 21-34)

---

**Выберите любой удобный способ сборки - все они дают одинаковый результат!** 🚀