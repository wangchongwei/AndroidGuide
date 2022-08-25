package com.example.project.proxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class TestProxyInvocationHandler: InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any {
        var size = args?.size

        for (i in 0 until size!!) {
            println("第${i}个参数: ${args!![i]}")
        }


        return 12
    }
}