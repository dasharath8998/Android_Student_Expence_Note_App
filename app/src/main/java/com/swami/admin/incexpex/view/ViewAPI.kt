package com.swami.admin.incexpex.view

import android.database.Cursor

interface ViewAPI {
    fun saveOutput(msg:String)
    fun readOutput(c:Cursor)
    fun deleteOutput(msg:String)
}