package com.simple.keyboard

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*

data class MainUiState(
    val debugMode: Boolean = false,
    val debugLogs: List<String> = emptyList(),
    val status: String = "Готов к работе"
)

class MainViewModel : ViewModel() {
    
    private val _uiState = MutableStateFlow(MainUiState())
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
    
    private val dateFormat = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    
    fun toggleDebugMode() {
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            debugMode = !currentState.debugMode
        )
        
        if (currentState.debugMode) {
            addDebugLog("Debug mode disabled")
            updateStatus("Debug выключен")
        } else {
            addDebugLog("Debug mode enabled")
            updateStatus("Debug включен")
        }
    }
    
    fun startFloatingButton(context: Context) {
        addDebugLog("Starting floating button service...")
        
        try {
            val intent = Intent(context, FloatingButtonService::class.java)
            context.startForegroundService(intent)
            
            addDebugLog("Floating button service started")
            updateStatus("Плавающая кнопка показана")
        } catch (e: Exception) {
            addDebugLog("Error starting floating button: ${e.message}")
            updateStatus("Ошибка: ${e.message}")
        }
    }
    
    fun addDebugLog(message: String) {
        val currentState = _uiState.value
        val timestamp = dateFormat.format(Date())
        val logEntry = "[$timestamp] $message"
        
        val newLogs = (currentState.debugLogs + logEntry).takeLast(20) // Последние 20 записей
        
        _uiState.value = currentState.copy(
            debugLogs = newLogs
        )
    }
    
    fun updateStatus(status: String) {
        val currentState = _uiState.value
        _uiState.value = currentState.copy(
            status = status
        )
    }
    
    companion object {
        @Volatile
        private var INSTANCE: MainViewModel? = null
        
        fun getInstance(): MainViewModel {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: MainViewModel().also { INSTANCE = it }
            }
        }
    }
}