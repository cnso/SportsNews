package org.jash.mylibrary.network

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.jash.mylibrary.model.Res
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class LiveDataAdapterFactory: CallAdapter.Factory() {
    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {
        return when (getRawType(returnType)) {
            LiveData::class.java -> LiveDataAdapter<Any>(getParameterUpperBound(0,
                returnType as ParameterizedType
            ))
            else -> null
        }
    }
}
class LiveDataAdapter<T>(val mType: Type):CallAdapter<T, LiveData<T>> {
    override fun responseType(): Type = mType

    override fun adapt(call: Call<T>): LiveData<T> {
        val liveData = MutableLiveData<T>()
        call.enqueue(object :Callback<T>{
            override fun onResponse(call: Call<T>, response: Response<T>) {
                liveData.postValue(response.body())
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                val res = Res<Any?>(-1, t.message ?: "网络错误", null)
                liveData.postValue(res as T)
            }

        })
        return liveData
    }

}