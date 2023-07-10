package org.jash.mylibrary.network

import androidx.databinding.ObservableField
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.jash.mylibrary.model.User
import org.jash.mylibrary.network.LiveDataAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
var token: String? = null
var user: User? = null
val client = OkHttpClient.Builder()
    .addInterceptor {
        val builder = it.request().newBuilder()
        token?.let {token ->
            builder.header("sn-token", token)
        }
        it.proceed(builder.build())
    }.build()
val retrofit = Retrofit.Builder()
    .baseUrl("http://43.143.157.87:8888/")
    .client(client)
    .addCallAdapterFactory(LiveDataAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
        .setDateFormat("YYYY-MM-dd HH:mm:ss")
        .create()))
    .build()
val service = retrofit.create(Service::class.java)