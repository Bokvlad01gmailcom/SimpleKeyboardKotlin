docker run --rm -v $(pwd):/workspace cimg/android:2023.10 bash -c 'cd /workspace && chmod +x gradlew && ./gradlew assembleDebug'
