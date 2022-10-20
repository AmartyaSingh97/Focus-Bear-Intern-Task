package com.example.alarmapp

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var mBtn : FloatingActionButton? =null
    private var time : Long = 0
    private var temp : Int = 0
    private var alarmManager : AlarmManager? = null
    private var pendingIntent : PendingIntent? = null
    private var list : ArrayList<PendingIntent> = ArrayList()
    private var layout : ConstraintLayout? = null


    @SuppressLint("UnspecifiedImmutableFlag")
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mBtn = findViewById(R.id.alarmButton)
        alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        layout = findViewById(R.id.parent_layout)
        list = ArrayList()


        mBtn?.setOnClickListener {
            val intent = Intent(this, AlarmReceiver::class.java)
            time += 5
            pendingIntent = PendingIntent.getBroadcast(this, temp, intent, 0)
            alarmManager?.setExact(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + time * 60000, pendingIntent)
            list.add(pendingIntent!!)
            temp++
            Log.d("Alarm","Total $temp alarms")
            Toast.makeText(this, "Alarm set for $time minute from now", Toast.LENGTH_SHORT).show()
        }
    }

   override fun onDestroy() {
        super.onDestroy()
        for (i in 0 until list.size) {
            alarmManager?.cancel(list[i])
        }
        list.clear()
        temp = 0
        time = 0
    }

}