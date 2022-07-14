package com.example.project.Binder.impls

import android.os.Binder
import android.os.IBinder
import android.os.Parcel
import com.example.project.Binder.bean.Dog
import com.example.project.Binder.interfaces.IDogManager
import com.example.project.Binder.interfaces.IDogManager.Companion.DESCRIPTOR
import com.example.project.Binder.interfaces.IDogManager.Companion.TRANSACTION_addDog
import com.example.project.Binder.interfaces.IDogManager.Companion.TRANSACTION_getDogList
import java.lang.Exception


abstract class DogManagerImpl : Binder(), IDogManager {

    init {
        // 相当于注册
        attachInterface(this, DESCRIPTOR)
    }

    companion object {
        fun asInterface(binder: IBinder):IDogManager? {
            if(binder == null) return null
            var iin = binder.queryLocalInterface(DESCRIPTOR)
            if(iin != null && iin is IDogManager) {
                // 在同一进程时，直接返回对象
                println("在统一进程时，直接返回对象")
                return iin
            }
            // 不在同一进程，返回代理对象
            println("不在同一进程，返回代理对象")
            return Proxy(binder)
        }

        class Proxy(var remote: IBinder) : DogManagerImpl() {

            override fun asBinder(): IBinder {
                return remote
            }

            override fun getInterfaceDescriptor(): String {
                return  DESCRIPTOR
            }

            override fun getDogList(): List<Dog> {
                var _data = Parcel.obtain()
                var _reply = Parcel.obtain()
                var _result: java.util.ArrayList<Dog>? = null
                try {
                    _data.writeInterfaceToken(DESCRIPTOR)
                    remote.transact(TRANSACTION_getDogList,_data,_reply,0)
                    _reply.readException()
                    _result = _reply.createTypedArrayList(Dog.CREATOR)
                    println("proxy getData => ${_result}")
                }catch (e: Exception) {
                    e.printStackTrace()
                    println("error => ${e.message}")
                }finally {
                    _data.recycle()
                    _reply.recycle()
                }
                return _result!!.toList()
            }

            override fun addDog(dog: Dog?) {
                var _data = Parcel.obtain()
                var _reply = Parcel.obtain()
                try {
                    _data.writeInterfaceToken(DESCRIPTOR)
                    if(dog != null) {
                        _data.writeInt(1)
                        dog.writeToParcel(_data,0)
                    } else {
                        _data.writeInt(0)
                    }
                    remote.transact(TRANSACTION_addDog, _data, _reply, 0)
                    println("proxy addDog => ${_data}")
                    _reply.readException()
                }catch (e: Exception) {
                    e.printStackTrace()
                }finally {
                    _data.recycle()
                    _reply.recycle()
                }
            }

        }
    }
    // 将当前对象包装成 IBinder
    override fun asBinder(): IBinder {
        return this
    }

    override fun onTransact(code: Int, data: Parcel, reply: Parcel?, flags: Int): Boolean {
        // 根据code匹配调用对应的函数
        when(code){
            INTERFACE_TRANSACTION -> {
                reply?.writeString(DESCRIPTOR)
                return true
            }

            TRANSACTION_getDogList -> {
                // 获取数据时
                data.enforceInterface(DESCRIPTOR)
                var _result = getDogList()
                reply?.writeNoException()
                // 输出结果
                reply?.writeTypedList(_result)
                return true
            }

            TRANSACTION_addDog -> {
                data.enforceInterface(DESCRIPTOR)
                var _arg0: Dog? = null
                if(data.readInt() != 0) {
                    _arg0 = Dog.CREATOR.createFromParcel(data)
                } else {
                    _arg0 = null
                }
                addDog(_arg0)
                reply?.writeNoException()
                return true
            }

        }
        return super.onTransact(code, data, reply, flags)
    }


}