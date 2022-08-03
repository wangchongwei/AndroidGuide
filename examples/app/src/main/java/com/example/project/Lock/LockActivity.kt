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
import java.lang.Runnable
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock

class LockActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLockBinding
    private var liveData: MutableLiveData<String> = MutableLiveData("defaultValue")

    @Volatile
    private var num = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLockBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()

        initData()

    }

    private fun initData() {

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

        binding.deadLock.setOnClickListener {
            val any1 = Any()
            val any2 = Any()
            val thread1: Thread = Thread(TestDeadLock(true, any1, any2))
            val thread2: Thread = Thread(TestDeadLock(false, any1, any2))

            thread1.start()
            thread2.start()
        }
    }

    private fun testLock() = runBlocking {
        var i = 0
        val jobs = mutableListOf<Job>()
        var lock = ReentrantLock()
        // kotlin中的锁
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

// synchronized 死锁
class TestDeadLock(var flag: Boolean, var any1: Any, var any2: Any) : Runnable {

    override fun run() {
        if(flag) {
            synchronized(any1) {
                println("${flag} 线程: 获取到any1的锁")
                Thread.sleep(1000)

                synchronized(any2) {
                    println("${flag} 线程: 获取到any2的锁")
                }
            }
        } else {
            synchronized(any2) {
                println("${flag} 线程: 获取到any2的锁")
                Thread.sleep(1000)

                synchronized(any1) {
                    println("${flag} 线程: 获取到any1的锁")
                }
            }
        }
        println("${flag} 线程: 未出现死锁!!!")
    }
}


class DeadLockTest(var flag: Boolean, var lock1: Lock, var lock2: Lock): Thread() {

    override fun run() {
        super.run()
        if (flag) {
           lock1.lock()
            println("${flag} 线程获取到 lock1")
           Thread.sleep(1000)
            lock2.lock()
            println("${flag} 线程获取到 lock2")

            lock1.unlock()
            lock2.unlock()
        } else {
            lock2.lock()
            println("${flag} 线程获取到 lock1")
            Thread.sleep(1000)
            lock1.lock()
            println("${flag} 线程获取到 lock2")

            lock2.unlock()
            lock1.unlock()
        }
    }
}
