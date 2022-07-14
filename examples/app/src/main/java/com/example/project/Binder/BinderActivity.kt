package com.example.project.Binder

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.project.Binder.bean.Dog
import com.example.project.Binder.impls.DogManagerImpl
import com.example.project.Binder.interfaces.IDogManager
import com.example.project.databinding.ActivityBinderBinding

class BinderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBinderBinding

    private var mService: IDogManager? = null

    var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            println("onServiceConnected => ${service}")
            mService = DogManagerImpl.asInterface(service!!)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            mService = null
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBinderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        binding.startBinder.setOnClickListener {
            bindService(Intent(this, RemoteService::class.java), serviceConnection, BIND_AUTO_CREATE)
        }

        binding.getData.setOnClickListener {
            if (mService != null) {
                println("开始获取所有数据")
                mService?.getDogList()
            }
        }

        binding.putData.setOnClickListener {
            if (mService != null) {
                println("开始添加一条数据")
                var  dog = Dog()
                dog.gender = 1
                dog.name = "jack"
                mService?.addDog(dog)

            }
        }
    }
}