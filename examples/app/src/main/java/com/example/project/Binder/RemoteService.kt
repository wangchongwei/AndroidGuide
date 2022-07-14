package com.example.project.Binder

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.project.Binder.bean.Dog
import com.example.project.Binder.impls.DogManagerImpl
import java.util.ArrayList

class RemoteService: Service() {

    var dogs = ArrayList<Dog>()

    val mBinder = object : DogManagerImpl() {
        override fun getDogList(): List<Dog> {
            println("RemoteService => getDogList: ${dogs}")
            return dogs
        }

        override fun addDog(dog: Dog?) {
            if(dog != null) {
                dogs.add(dog)
            }
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }
}