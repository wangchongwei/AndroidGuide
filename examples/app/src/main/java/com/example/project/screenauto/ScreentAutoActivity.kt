package com.example.project.screenauto

import android.app.Activity
import android.content.ComponentCallbacks
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import com.example.project.databinding.ActivityScreentAutoBinding
import me.jessyan.autosize.internal.CancelAdapt
import me.jessyan.autosize.internal.CustomAdapt


class ScreentAutoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityScreentAutoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScreentAutoBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }




}