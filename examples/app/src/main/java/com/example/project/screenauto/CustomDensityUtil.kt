package com.example.project.screenauto

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.res.Configuration
import android.util.DisplayMetrics


object CustomDensityUtil {

    private var sNoncompatDensity = 0f
    private var sNoncompatScaledDensity = 0f


    /**
     * 今日头条适配方案
     *
     * @param activity
     * @param application
     */
    fun setCustomDensity(activity: Activity, application: Application) {
        //通过资源文件getResources类获取DisplayMetrics
        val appDisplayMetrics: DisplayMetrics = application.getResources().getDisplayMetrics()
        if (sNoncompatDensity == 0f) {
            //保存之前density值
            sNoncompatDensity = appDisplayMetrics.density
            //保存之前scaledDensity值，scaledDensity为字体的缩放因子，正常情况下和density相等，但是调节系统字体大小后会改变这个值
            sNoncompatScaledDensity = appDisplayMetrics.scaledDensity
            //监听设备系统字体切换
            application.registerComponentCallbacks(object : ComponentCallbacks {
                override fun onConfigurationChanged(newConfig: Configuration) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        //调节系统字体大小后改变的值
                        sNoncompatScaledDensity =
                            application.getResources().getDisplayMetrics().scaledDensity
                    }
                }

                override fun onLowMemory() {}
            })
        }

        //获取以设计图总宽度360dp下的density值
        // 获取设备实际宽度与设计稿360的比例
        val targetDensity = (appDisplayMetrics.widthPixels / 375).toFloat()
        //通过计算之前scaledDensity和density的比获得scaledDensity值
        // 获取字体的缩放
        val targetScaleDensity = targetDensity * (sNoncompatScaledDensity / sNoncompatDensity)
        //获取以设计图总宽度360dp下的dpi值
        val targetDensityDpi = (160 * targetDensity).toInt()
        //设置系统density值
        appDisplayMetrics.density = targetDensity
        //设置系统scaledDensity值
        appDisplayMetrics.scaledDensity = targetScaleDensity
        //设置系统densityDpi值
        appDisplayMetrics.densityDpi = targetDensityDpi

        //获取当前activity的DisplayMetrics
        val activityDisplayMetrics = activity.resources.displayMetrics
        //设置当前activity的density值
        activityDisplayMetrics.density = targetDensity
        //设置当前activity的scaledDensity值
        activityDisplayMetrics.scaledDensity = targetScaleDensity
        //设置当前activity的densityDpi值
        activityDisplayMetrics.densityDpi = targetDensityDpi
    }
}