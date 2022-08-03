package com.example.project.aidl.server

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.project.IMyAidlInterface

class MyAidlService : Service() {

    var binder = object : IMyAidlInterface.Stub() {
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {
            TODO("Not yet implemented")
        }

        override fun add(num1: Int, num2: Int): Int {
            return num1 + num2
        }

    }

    override fun onBind(intent: Intent): IBinder {
        return binder
    }
}