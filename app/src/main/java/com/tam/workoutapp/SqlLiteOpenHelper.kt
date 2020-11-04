package com.tam.workoutapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlLiteOpenHelper(context : Context, factory : SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context,
    DATABASE_NAME,factory, DATABASE_VERSION) {

    companion object{

        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "sevenMinutesWorkOut.sql"
        private val TABLE_HISTORY = "History"
        private val COLUMN_ID = "id"
        private val COLUMN_COMPLETED_DATE = "Completed_date"

    }

    override fun onCreate(p0: SQLiteDatabase?) {

        val CREATE_HISTORY_TABLE = ("CREATE TABLE " +
                TABLE_HISTORY + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_COMPLETED_DATE
                + " TEXT)") // Create History Table Query.
        if (p0 != null) {
            p0.execSQL(CREATE_HISTORY_TABLE)
        } // Executing the create table query.
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {

        p0?.execSQL("DROP TABLE IF EXITS"+ TABLE_HISTORY)
        onCreate(p0)
    }

    fun addDate(date: String) {
        val values =
            ContentValues() // Creates an empty set of values using the default initial size
        values.put(
            COLUMN_COMPLETED_DATE,
            date
        ) // Putting the value to the column along with the value.
        val db =
            this.writableDatabase // Create and/or open a database that will be used for reading and writing.
        db.insert(TABLE_HISTORY, null, values) // Insert query is return
        db.close() // Database is closed after insertion.
    }

    fun collectAllData():ArrayList<String>{
        var list = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_HISTORY",null)
        while(cursor.moveToNext()){
            val dateValue = cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE))
            list.add(dateValue)
        }
        return list

    }







}