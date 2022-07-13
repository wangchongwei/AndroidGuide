package com.example.project.Lock

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.example.project.databinding.ActivityLockBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.sync.Mutex
import java.util.concurrent.locks.ReentrantLock

class LockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLockBinding
    private var liveData: MutableLiveData<String> = MutableLiveData("defaultValue")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLockBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

    }

    private fun initView() {
        binding.start.setOnClickListener {
            GlobalScope.launch {
                for (i in 1..10) {
                    flow<String> {
                        var currentThreadName = Thread.currentThread().name
                        println("emit => ${currentThreadName}")
                        delay(500)
                        emit(currentThreadName)
                    }.flowOn(Dispatchers.IO)
                        .collect {
                            liveData.postValue(it)
                        }
                }
            }
        }
        var observer: Observer<String> = Observer {
            binding.threadName.text = "currentThreadName: ${it}"
        }
        liveData.observe(this, observer)

        binding.startLock.setOnClickListener {
            testLock()
        }
    }

    @Synchronized
    private fun test() {

    }

    private fun testLock() = runBlocking {
        var i = 0
        val jobs = mutableListOf<Job>()
        var lock = ReentrantLock()

        val mutex = Mutex()

        repeat(10) {
            val job = launch (Dispatchers.Default){
                repeat(1000){
//                    synchronized(true) {
//                        i ++
//                    }
//                    lock.lock()
//                    i++
//                    lock.unlock()

                    mutex.lock()
                    i++
                    mutex.unlock()
                }
            }
            jobs.add(job)
        }
        jobs.joinAll()
        println("i = ${i}")
    }

}

