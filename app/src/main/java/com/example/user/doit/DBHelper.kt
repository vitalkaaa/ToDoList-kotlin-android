package com.example.user.doit

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log


class DBHelper(context: Context)// конструктор суперкласса
    : SQLiteOpenHelper(context, "myDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        Log.d("DB", "--- onCreate database ---")
        db.execSQL("create table mytable ("
                + "id integer primary key autoincrement,"
                + "title text,"
                + "content text,"
                + "date integer);")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {

    }
}