package com.example.alarmmanager

import android.annotation.SuppressLint
import android.app.*
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.TimePicker
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import java.text.DateFormat
import java.util.Calendar

class MainActivity : AppCompatActivity(), TimePickerDialog.OnTimeSetListener {
    private var mTextView: TextView? = null
     override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        mTextView = findViewById(R.id.textView)
        val buttonTimePicker = findViewById<Button>(R.id.button_timepicker)
        buttonTimePicker.setOnClickListener {
            val timePicker: DialogFragment = TimePickerFragment()
            timePicker.show(supportFragmentManager, "time picker")
        }
        val buttonCancelAlarm = findViewById<Button>(R.id.button_cancel)
        buttonCancelAlarm.setOnClickListener {
             cancelAlarm()
        }
    }
    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        val c: Calendar = Calendar.getInstance()
        c.set(Calendar.HOUR_OF_DAY, hourOfDay)
        c.set(Calendar.MINUTE, minute)
        c.set(Calendar.SECOND, 0)
        updateTimeText(c)
        startAlarm(c)
    }

    private fun updateTimeText(c: Calendar) {
        var timeText = "Alarm set for: "
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.time)
        mTextView!!.text = timeText
    }

    private fun startAlarm(c: Calendar) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1)
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.timeInMillis, pendingIntent)

//        AlarmManager.RTC نوع منبه بس وقت حقيقى مش وقت حقيقى لايستطيع تنبيه المستخدم عندما تكون شاشة هاتفه مغلقه
//        AlarmManager.RTC_WAKEUP نوع منبه بس وقت حقيقى مش وقت حقيقى  يستطيع تنبيه المستخدم عندما تكون شاشة هاتفه مغلقه
//        AlarmManager.ELAPSED_REALTIME  نوع منبه بس وقت نسبى مش وقت حقيقى لايستطيع تنبيه المستخدم عندما تكون شاشة هاتفه مغلقه
//        AlarmManager.ELAPSED_REALTIME_WAKEUP  نوع منبه  بس وقت نسبى مش وقت حقيقى يستطيع تنبيه المستخدم عندما تكون شاشة هاتفه مغلقه

//        alarmManager.setAndAllowWhileIdle()  تنفذ الامر  لما موب يكون خامل
//        alarmManager.setExactAndAllowWhileIdle() تنفذ الامر  لما موب يكون خامل بدقه اكثر
//        alarmManager.setInexactRepeating()    عشان تكرار وقت بس مش بتسهلك ريسورس
//        alarmManager.setRepeating()  عشان تكرار وقت بس بتسهلك ريسورس
//        alarmManager.setExact()  تنفذ الامر بس مش هيتنفذ لما موب يكون خامل
    }

    @SuppressLint("SetTextI18n")
    private fun cancelAlarm() {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, AlertReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0)
        alarmManager.cancel(pendingIntent)
        mTextView!!.text = "Alarm canceled"
    }
}