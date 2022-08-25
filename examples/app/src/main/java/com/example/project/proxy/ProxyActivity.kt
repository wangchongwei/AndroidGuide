package com.example.project.proxy

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.project.Binder.impls.DogManagerImpl
import com.example.project.databinding.ActivityProxyBinding
import java.lang.reflect.Proxy

class ProxyActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProxyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProxyBinding.inflate(layoutInflater)
        setContentView(binding.root)


        testProxy()
    }

    private fun testProxy() {
        var service = TestProxyInterface::class.java

        var proxy = Proxy.newProxyInstance(service.classLoader, arrayOf(service), TestProxyInvocationHandler()) as TestProxyInterface

        var result = proxy.add(10,2)

        println("result => $result")
    }
}