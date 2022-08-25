package com.example.project.glide

import android.Manifest
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.ConnectivityManager.TYPE_WIFI
import android.net.wifi.WifiManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.project.databinding.ActivityGlideBinding
import java.util.*

class GlideActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGlideBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGlideBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPermission()
        initView()
    }

    private fun initPermission() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var permissions = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.ACCESS_WIFI_STATE)
            for (i in 0..(permissions.size - 1)) {
                if(checkSelfPermission(permissions[i]) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(permissions, 101)
                }
            }
        }

    }

    private fun initView() {
        Glide.with(this).load("https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fcs.copart.com%2Fv1%2FAUTH_svc.pdoc00001%2FHPX14%2F206c2ed6-4b08-49ce-8d6d-e61fa54bd786.JPG&refer=http%3A%2F%2Fcs.copart.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1663146340&t=f19c8fef1cb672d4b78a60e0ad2e41a5").into(binding.internetImage)

        binding.all.setOnClickListener {
            var pm = packageManager


            var packageList = pm.getInstalledPackages(0)
            for (i in 0..(packageList.size - 1)) {
                System.out.println("packageName: " + packageList[i].packageName)
                System.out.println("ApplicationName: " + packageList[i].applicationInfo.loadLabel(pm).toString())
            }
        }



        binding.bssid.setOnClickListener {
            var wifiManager = getApplicationContext().getSystemService(WIFI_SERVICE) as WifiManager
            if(wifiManager == null) System.out.println("wifiManager == null")
            var wifiConnect = wifiManager.connectionInfo
            System.out.println("获取wifi BSSID：" + wifiConnect.bssid)
            System.out.println("获取wifi SSID：" + wifiConnect.ssid)
        }
    }
}