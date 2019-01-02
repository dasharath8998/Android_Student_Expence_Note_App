package com.swami.admin.incexpex.presenter

import com.swami.admin.incexpex.beans.IncExpData

interface PresentAPI {
    fun save(bean: IncExpData)
    fun read()
    fun delete(v_desc:String)
}