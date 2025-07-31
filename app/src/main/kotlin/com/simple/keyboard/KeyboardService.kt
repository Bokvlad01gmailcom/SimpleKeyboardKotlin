package com.simple.keyboard

import android.inputmethodservice.InputMethodService
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.util.Log

class KeyboardService : InputMethodService() {
    
    companion object {
        private const val TAG = "SimpleKeyboard"
        private var instance: KeyboardService? = null
        
        fun getInstance(): KeyboardService? = instance
    }
    
    override fun onCreate() {
        super.onCreate()
        instance = this
        Log.d(TAG, "KeyboardService created")
        
        // Добавляем лог в UI если возможно
        try {
            MainViewModel.getInstance().addDebugLog("KeyboardService created")
        } catch (e: Exception) {
            Log.w(TAG, "Could not add debug log: ${e.message}")
        }
    }
    
    override fun onDestroy() {
        super.onDestroy()
        instance = null
        Log.d(TAG, "KeyboardService destroyed")
        
        try {
            MainViewModel.getInstance().addDebugLog("KeyboardService destroyed")
        } catch (e: Exception) {
            Log.w(TAG, "Could not add debug log: ${e.message}")
        }
    }
    
    override fun onCreateInputView(): View? {
        Log.d(TAG, "onCreateInputView called")
        
        try {
            MainViewModel.getInstance().addDebugLog("Input view created - showing floating button")
        } catch (e: Exception) {
            Log.w(TAG, "Could not add debug log: ${e.message}")
        }
        
        // Возвращаем null - у нас нет видимой клавиатуры
        // Вся логика в плавающей кнопке
        return null
    }
    
    override fun onStartInputView(info: EditorInfo?, restarting: Boolean) {
        super.onStartInputView(info, restarting)
        Log.d(TAG, "onStartInputView called")
        
        try {
            MainViewModel.getInstance().addDebugLog("Input view started")
        } catch (e: Exception) {
            Log.w(TAG, "Could not add debug log: ${e.message}")
        }
    }
    
    override fun onFinishInputView(finishingInput: Boolean) {
        super.onFinishInputView(finishingInput)
        Log.d(TAG, "onFinishInputView called")
        
        try {
            MainViewModel.getInstance().addDebugLog("Input view finished")
        } catch (e: Exception) {
            Log.w(TAG, "Could not add debug log: ${e.message}")
        }
    }
    
    /**
     * Отправляет Enter в активное поле ввода
     */
    fun sendEnter() {
        val ic = currentInputConnection
        if (ic != null) {
            // Отправляем KeyEvent для Enter
            ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER))
            ic.sendKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_ENTER))
            
            Log.d(TAG, "Enter key sent")
            
            try {
                MainViewModel.getInstance().addDebugLog("Enter key sent successfully")
            } catch (e: Exception) {
                Log.w(TAG, "Could not add debug log: ${e.message}")
            }
        } else {
            Log.w(TAG, "No input connection available")
            
            try {
                MainViewModel.getInstance().addDebugLog("Error: No input connection available")
            } catch (e: Exception) {
                Log.w(TAG, "Could not add debug log: ${e.message}")
            }
        }
    }
}