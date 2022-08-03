package com.example.project.aidl

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.project.IMyAidlInterface
import com.example.project.databinding.ActivityAidlBinding
import com.example.project.aidl.server.MyAidlService

class AidlActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAidlBinding

    private lateinit var service: ServiceConnection

    private lateinit var remoteServiceProxy: IMyAidlInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAidlBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initData()

        initView()
    }

    private fun initData() {
        service = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                remoteServiceProxy = IMyAidlInterface.Stub.asInterface(service)
            }

            override fun onServiceDisconnected(name: ComponentName?) {

            }

        }
    }

    private fun initView() {
        binding.binderServer.setOnClickListener {
            bindService(Intent(this, MyAidlService::class.java), service, BIND_AUTO_CREATE)
        }

        binding.add.setOnClickListener {
            var result = remoteServiceProxy.add(1, 5)
            println("result: $result")
        }

        binding.unbind.setOnClickListener {
            unbindService(service)
        }
    }
}