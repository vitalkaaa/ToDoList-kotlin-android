package com.example.user.doit

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.widget.BaseAdapter
import android.widget.EditText
import java.util.*
import java.util.Calendar


object Calendar{
    var cases: MutableList<Event> = mutableListOf()
    private val dateAndTime  = Calendar.getInstance()!!
    lateinit var dbHelper: DBHelper
    var lastID = 0

    fun initDB(d: DBHelper){
        dbHelper = d
    }

    fun addEvent(e: Event){
        val db = dbHelper.writableDatabase
        val cv = ContentValues()
        cv.put("title", e.title)
        cv.put("content", e.content)
        cv.put("date", e.date.time)
        db.insert("mytable", null, cv)
        cases.add(e)
        cases.sortBy { it.date }
    }

    fun loadEvents(){
        val db = dbHelper.writableDatabase
        val c = db.query("mytable", null, null, null, null, null, null)
        cases.clear()

        if(c.moveToFirst()) {
            val titleInd: Int = c.getColumnIndex("title")
            val textInd = c.getColumnIndex("content")
            val dateInd = c.getColumnIndex("date")
            val idInd = c.getColumnIndex("id")

            do {
                cases.add(Event(Date(c.getLong(dateInd)),
                        c.getString(titleInd),
                        c.getString(textInd),
                        c.getInt(idInd)))
            } while(c.moveToNext())
        }
        c.close()
        cases.sortBy { it.date }
    }

    fun removeEvent(pos: Int){
        val db = dbHelper.writableDatabase
        db.delete("mytable", "id = ${cases[pos].id}", null)
        Log.d("remove", "id = ${cases[pos].id}")
        cases.removeAt(pos)
    }

    fun createEvent(ctx: Context, adapter: BaseAdapter){
        dateDialog(ctx, adapter)
    }

    private fun dateDialog(ctx: Context, adapter: BaseAdapter) {
        DatePickerDialog(ctx, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            dateAndTime.set(Calendar.YEAR, year)
            dateAndTime.set(Calendar.MONTH, monthOfYear)
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            timeDialog(ctx, adapter)

        }, dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show()
    }

    private fun timeDialog(ctx: Context, adapter: BaseAdapter) {
        TimePickerDialog(ctx, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
            dateAndTime.set(Calendar.MINUTE, minute)
            contentDialog(ctx, adapter)
        },
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show()
    }

    private fun contentDialog(ctx: Context, adapter: BaseAdapter) {
        val builder = AlertDialog.Builder(ctx)
        val inflater = LayoutInflater.from(ctx)//ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val v = inflater.inflate(R.layout.dialog, null)
        val title: EditText = v.findViewById(R.id.edTitle)
        val text: EditText = v.findViewById(R.id.edText)

        builder.setView(v)
                .setCancelable(true)
                .setTitle("Input your event")
                .setPositiveButton("OK", {d, i ->
                    this.addEvent(Event(Date(com.example.user.doit.Calendar.dateAndTime.timeInMillis),
                            title.text.toString(),
                            text.text.toString()))
                    adapter.notifyDataSetChanged()
                    d.cancel()
                })

        val alert = builder.create()
        alert.show()
    }
}



