package com.simple.keyboard

import android.app.*
import android.content.Intent
import android.graphics.PixelFormat
import android.os.Build
import android.os.IBinder
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.ImageButton
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat

class FloatingButtonService : Service() {
    
    companion object {
        private const val TAG = "FloatingButtonService"
        private const val NOTIFICATION_ID = 1
        private const val CHANNEL_ID = "floating_button_channel"
    }
    
    private var windowManager: WindowManager? = null
    private var floatingButton: View? = null
    private var isButtonShowing = false
    
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "FloatingButtonService created")
        
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
        createNotificationChannel()
        
        try {
            MainViewModel.getInstance().addDebugLog("FloatingButtonService created")
        } catch (e: Exception) {
            Log.w(TAG, "Could not add debug log: ${e.message}")
        }
    }
    
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "FloatingButtonService started")
        
        // Запускаем как foreground service
        startForeground(NOTIFICATION_ID, createNotification())
        
        if (Settings.canDrawOverlays(this)) {
            showFloatingButton()
        } else {
            Log.w(TAG, "No overlay permission")
            try {
                MainViewModel.getInstance().addDebugLog("Error: No overlay permission")
            } catch (e: Exception) {
                Log.w(TAG, "Could not add debug log: ${e.message}")
            }
            stopSelf()
        }
        
        return START_STICKY
    }
    
    override fun onDestroy() {
        super.onDestroy()
        hideFloatingButton()
        Log.d(TAG, "FloatingButtonService destroyed")
        
        try {
            MainViewModel.getInstance().addDebugLog("FloatingButtonService destroyed")
        } catch (e: Exception) {
            Log.w(TAG, "Could not add debug log: ${e.message}")
        }
    }
    
    override fun onBind(intent: Intent?): IBinder? = null
    
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Floating Button Service",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Service for floating keyboard button"
                setShowBadge(false)
            }
            
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
    
    private fun createNotification(): Notification {
        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        
        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Simple Keyboard")
            .setContentText("Плавающая кнопка активна")
            .setSmallIcon(android.R.drawable.ic_input_keyboard)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .setSilent(true)
            .build()
    }
    
    private fun showFloatingButton() {
        if (isButtonShowing) return
        
        try {
            // Создаем кнопку
            floatingButton = LayoutInflater.from(this)
                .inflate(R.layout.floating_button, null)
            
            val button = floatingButton?.findViewById<ImageButton>(R.id.floating_button)
            button?.setOnClickListener {
                onFloatingButtonClick()
            }
            
            // Параметры окна
            val layoutFlag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else {
                @Suppress("DEPRECATION")
                WindowManager.LayoutParams.TYPE_PHONE
            }
            
            val params = WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                layoutFlag,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT
            ).apply {
                gravity = Gravity.TOP or Gravity.END
                x = 20
                y = 100
            }
            
            // Добавляем в WindowManager
            windowManager?.addView(floatingButton, params)
            isButtonShowing = true
            
            // Добавляем возможность перетаскивания
            makeButtonDraggable(params)
            
            Log.d(TAG, "Floating button shown")
            try {
                MainViewModel.getInstance().addDebugLog("Floating button shown successfully")
            } catch (e: Exception) {
                Log.w(TAG, "Could not add debug log: ${e.message}")
            }
            
        } catch (e: Exception) {
            Log.e(TAG, "Error showing floating button", e)
            try {
                MainViewModel.getInstance().addDebugLog("Error showing floating button: ${e.message}")
            } catch (ex: Exception) {
                Log.w(TAG, "Could not add debug log: ${ex.message}")
            }
        }
    }
    
    private fun hideFloatingButton() {
        if (!isButtonShowing) return
        
        try {
            floatingButton?.let { button ->
                windowManager?.removeView(button)
                floatingButton = null
                isButtonShowing = false
                
                Log.d(TAG, "Floating button hidden")
                try {
                    MainViewModel.getInstance().addDebugLog("Floating button hidden")
                } catch (e: Exception) {
                    Log.w(TAG, "Could not add debug log: ${e.message}")
                }
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error hiding floating button", e)
        }
    }
    
    private fun makeButtonDraggable(params: WindowManager.LayoutParams) {
        floatingButton?.setOnTouchListener(object : View.OnTouchListener {
            private var initialX = 0
            private var initialY = 0
            private var initialTouchX = 0f
            private var initialTouchY = 0f
            
            override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                when (event?.action) {
                    MotionEvent.ACTION_DOWN -> {
                        initialX = params.x
                        initialY = params.y
                        initialTouchX = event.rawX
                        initialTouchY = event.rawY
                        return true
                    }
                    MotionEvent.ACTION_MOVE -> {
                        params.x = initialX + (event.rawX - initialTouchX).toInt()
                        params.y = initialY + (event.rawY - initialTouchY).toInt()
                        windowManager?.updateViewLayout(floatingButton, params)
                        return true
                    }
                    MotionEvent.ACTION_UP -> {
                        val deltaX = kotlin.math.abs(event.rawX - initialTouchX)
                        val deltaY = kotlin.math.abs(event.rawY - initialTouchY)
                        
                        // Если движение было минимальным, считаем это кликом
                        if (deltaX < 10 && deltaY < 10) {
                            v?.performClick()
                        }
                        return true
                    }
                }
                return false
            }
        })
    }
    
    private fun onFloatingButtonClick() {
        Log.d(TAG, "Floating button clicked")
        
        try {
            MainViewModel.getInstance().addDebugLog("Floating button clicked - sending Enter")
        } catch (e: Exception) {
            Log.w(TAG, "Could not add debug log: ${e.message}")
        }
        
        // Отправляем Enter через KeyboardService
        val keyboardService = KeyboardService.getInstance()
        if (keyboardService != null) {
            keyboardService.sendEnter()
        } else {
            Log.w(TAG, "KeyboardService not available")
            try {
                MainViewModel.getInstance().addDebugLog("Error: KeyboardService not available")
            } catch (e: Exception) {
                Log.w(TAG, "Could not add debug log: ${e.message}")
            }
        }
    }
}