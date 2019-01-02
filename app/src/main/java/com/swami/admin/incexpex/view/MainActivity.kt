package com.swami.admin.incexpex.view

import android.app.DatePickerDialog
import android.database.Cursor
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.widget.SimpleCursorAdapter
import android.widget.DatePicker
import android.widget.Toast
import com.swami.admin.incexpex.R
import com.swami.admin.incexpex.beans.IncExpData
import com.swami.admin.incexpex.model.IncExpModel
import com.swami.admin.incexpex.presenter.PresentAPI
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(),ViewAPI {
    override fun deleteOutput(msg: String) {
        Toast.makeText(this@MainActivity,msg,Toast.LENGTH_LONG).show()
    }

    override fun readOutput(c: Cursor) {
        var from = arrayOf("date","money","_desc","type")
        var to = intArrayOf(R.id.tvDate,R.id.tvMoney,R.id.tvDesc,R.id.tvType)
        var cAdapter = SimpleCursorAdapter(this@MainActivity,R.layout.indiv,c,from,to,0)
        lView.adapter = cAdapter

        var exp_sum = 0
        var inc_sum = 0
        while(c.moveToNext()) {
            if (c.getString(4).toString() == "Expence") {
                exp_sum = exp_sum + c.getInt(2)
            } else{
                inc_sum = inc_sum + c.getInt(2)
            }
        }
        tvEsum.setText("Expence Sum: "+exp_sum.toString())
        tvIsum.setText("Income Sum: "+inc_sum.toString())
    }

    override fun saveOutput(msg: String) {
        Toast.makeText(this@MainActivity,msg,Toast.LENGTH_LONG).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDate.setOnClickListener {
            var c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this@MainActivity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                // Display Selected date in textbox
                etDate.setText("" + dayOfMonth.toString() + ", " + monthOfYear+1.toString() + ", " + year.toString())
            }, year, month, day)
            dpd.show()
        }

        btnRead.setOnClickListener {
            var model:PresentAPI = IncExpModel(this)
            model.read()
        }

        btnSave.setOnClickListener {
            var data = IncExpData(etDate.text.toString(),etMoney.text.toString().toInt(),etDesc.text.toString(),etType.selectedItem.toString())
            var model:PresentAPI = IncExpModel(this)
            model.save(data)
        }

        btnDelete.setOnClickListener {
            var model:PresentAPI = IncExpModel(this)
            model.delete(tvDelete.text.toString())
        }
    }
}
