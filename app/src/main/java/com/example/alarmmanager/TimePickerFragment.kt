package com.example.alarmmanager

import android.annotation.SuppressLint
import android.app.Dialog
import android.app.TimePickerDialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import java.util.*

class TimePickerFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val c :Calendar = Calendar.getInstance()
        val hour:Int = c.get(Calendar.HOUR_OF_DAY)
        val min:Int = c.get(Calendar.MINUTE)
        return TimePickerDialog(activity,activity as TimePickerDialog.OnTimeSetListener,hour,min,android.text.format.DateFormat.is24HourFormat(activity))
    }
}