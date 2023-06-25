package org.jash.sportsnews.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.jash.mylibrary.network.LiveDataAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val retrofit = Retrofit.Builder()
    .baseUrl("http://43.143.157.87:8888/")
    .addCallAdapterFactory(LiveDataAdapterFactory())
    .addConverterFactory(GsonConverterFactory.create(GsonBuilder()
        .setDateFormat("YYYY-MM-dd HH:mm:ss").create()))
    .build()
val service = retrofit.create(Service::class.java)