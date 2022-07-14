package com.example.project

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import com.example.project.Binder.BinderActivity
import com.example.project.Lock.LockActivity
import com.example.project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        initView()
    }

    private fun initView() {
        binding.lock.setOnClickListener { 
            startActivity(Intent(this, LockActivity::class.java))
        }

        binding.binder.setOnClickListener {
            startActivity(Intent(this, BinderActivity::class.java))
        }
    }
}