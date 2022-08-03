package com.example.project

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.media.projection.MediaProjectionManager
import android.os.Build
import android.os.Bundle
import android.util.Base64
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.project.Binder.BinderActivity
import com.example.project.Lock.LockActivity
import com.example.project.aidl.AidlActivity
import com.example.project.databinding.ActivityMainBinding
import java.io.ByteArrayOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        initData()
        initView()


    }

    private fun initData() {
        var map = HashMap<String,String>()
        map.put("item", "value")
        map.get("")
        map.remove("")


    }

    private fun initView() {
        binding.lock.setOnClickListener { 
            startActivity(Intent(this, LockActivity::class.java))
        }

        binding.binder.setOnClickListener {
            startActivity(Intent(this, BinderActivity::class.java))
        }

        binding.aidl.setOnClickListener {
            startActivity(Intent(this, AidlActivity::class.java))
        }

        binding.screen.setOnClickListener {
//            getScreenShotPower()
            screenshotView()
        }

    }


    private fun getScreenShotPower() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var mProjectionManager = getSystemService(Context.MEDIA_PROJECTION_SERVICE) as MediaProjectionManager
            if (mProjectionManager != null) {
                startActivityForResult(mProjectionManager.createScreenCaptureIntent(), 101)

            }
        }
    }

    // 开始截屏
    private fun screenshotView(): ByteArray? {
        val view: View = window.decorView
        // view.setDrawingCacheEnabled(true); // 设置缓存，可用于实时截图
        val bitmap =
            Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        view.draw(canvas)
        // view.setDrawingCacheEnabled(false); // 清空缓存，可用于实时截图

        binding.image.setImageBitmap(bitmap)

        return getBitmapByte(bitmap)
    }


    // 位图转 Base64 String
    private fun getBitmapString(bitmap: Bitmap?): String? {
        var result: String? = null
        var out: ByteArrayOutputStream? = null
        try {
            if (bitmap != null) {
                out = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
                out.flush()
                out.close()
                val bitmapBytes: ByteArray = out.toByteArray()
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            try {
                if (out != null) {
                    out.flush()
                    out.close()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return result
    }

    // 位图转 Byte
    private fun getBitmapByte(bitmap: Bitmap): ByteArray? {
        val out = ByteArrayOutputStream()
        // 参数1转换类型，参数2压缩质量，参数3字节流资源
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
        try {
            out.flush()
            out.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return out.toByteArray()
    }

}