package com.soft.guoni

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by 123456 on 2016/6/23.
 */

class MyDatePicker(context: Context?, theme: Int) : Dialog(context, theme), DatePicker.OnDateChangedListener {
    companion object {
        val formatString = "yyyy-MM-dd"
    }

    val datePickerStart: DatePicker by lazy {
        findViewById(R.id.datePickerStart) as DatePicker
    }
    val datePickerEnd: DatePicker by lazy {
        findViewById(R.id.datePickerEnd) as DatePicker
    }
    val buttonOK: Button by lazy {
        findViewById(R.id.buttonOK) as Button
    }
    val buttonCancel: Button by lazy {
        findViewById(R.id.buttonCancel) as Button
    }
    lateinit var dateStart: Date
    lateinit var dateEnd: Date
    var result = false

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.date_picker)
        buttonOK.setOnClickListener { result = true;dismiss() }
        buttonCancel.setOnClickListener { result = false;dismiss() }
    }

    override fun onDateChanged(view: DatePicker?, year: Int, monthOfYear: Int, dayOfMonth: Int) {
        val date = SimpleDateFormat(formatString).parse("${view?.year}-${view?.month}-${view?.dayOfMonth}")
        if (view == datePickerStart) dateStart = date else if (view == datePickerEnd) dateEnd = date
    }
}