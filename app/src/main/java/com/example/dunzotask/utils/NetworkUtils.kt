package com.example.dunzotask.utils

import io.objectbox.android.AndroidScheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object NetworkUtils {
    fun hasInternetConnection() : Single<Boolean>{
        return Single.fromCallable{
            try{
                val timeOut=1500
                val socket=Socket()
                val socketAddress=InetSocketAddress("8.8.8.8",53)

                socket.connect(socketAddress,timeOut)
                socket.close()
                true
            }
            catch (e:IOException){
                false
            }
        }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}