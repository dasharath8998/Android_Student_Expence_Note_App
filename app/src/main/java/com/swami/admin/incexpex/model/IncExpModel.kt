package com.swami.admin.incexpex.model

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.widget.SimpleCursorAdapter
import com.swami.admin.incexpex.R
import com.swami.admin.incexpex.beans.IncExpData
import com.swami.admin.incexpex.presenter.PresentAPI
import com.swami.admin.incexpex.view.MainActivity
import com.swami.admin.incexpex.view.ViewAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.indiv.view.*

class IncExpModel: PresentAPI {
    var mActivity:MainActivity? = null
    var dBase:SQLiteDatabase? = null
    constructor(view_interface: ViewAPI){
        mActivity = view_interface as MainActivity
        dBase = mActivity!!.openOrCreateDatabase("IncExpDB", Context.MODE_PRIVATE,null)
        dBase!!.execSQL("create table if not exists IncExpTable(_id integer primary key autoincrement, date varchar(20), money integer, _desc varchar(500), type varchar(20))")
    }

    override fun read() {

        var c = dBase!!.query("IncExpTable",null,null,null,null,null,null)
        mActivity!!.readOutput(c)
    }

    override fun save(bean: IncExpData) {
        var cv = ContentValues()
        cv.put("date",bean.date)
        cv.put("money",bean.money)
        cv.put("_desc",bean.desc)
        cv.put("type",bean.type)
        var status = dBase!!.insert("IncExpTable",null,cv)

        if(status != -1L){
            mActivity!!.saveOutput("Data inserted...")
        }else{
            mActivity!!.saveOutput("Data insertion is failed...")
        }

        read()
    }

    override fun delete(v_desc:String){
        var status =   dBase!!.delete("IncExpTable","_desc=?",arrayOf(v_desc))

        if(status != 0){
            mActivity!!.deleteOutput("Data Deleted")
            mActivity!!.tvDelete.setText("")
        }else{
            mActivity!!.deleteOutput("Data Deletion failed")
        }

        read()
    }
}