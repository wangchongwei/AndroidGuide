package com.example.project.Binder.interfaces

import android.os.IBinder
import android.os.IInterface
import com.example.project.Binder.bean.Dog

interface IDogManager : IInterface {

    companion object{
        val DESCRIPTOR = "com.example.project.Binder.interfaces.IDogManager"

        val TRANSACTION_getDogList = IBinder.FIRST_CALL_TRANSACTION + 0

        val TRANSACTION_addDog = IBinder.FIRST_CALL_TRANSACTION + 1
    }

    fun getDogList(): List<Dog>

    fun addDog(dog: Dog?)

}