package org.jash.mylibrary.network

import androidx.lifecycle.LiveData
import org.jash.mylibrary.model.Res
import org.jash.mylibrary.model.Category
import org.jash.mylibrary.model.Page
import org.jash.mylibrary.model.Record
import org.jash.mylibrary.model.User
import org.jash.mylibrary.model.VideoModel
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface Service {
    @GET("/api/nt/all")
    fun getCategory():LiveData<Res<List<Category>>>
    @GET("/api/news/page")
    fun getRecord(@Query("type") type:Int, @Query("page") page:Int, @Query("size") size:Int):LiveData<Res<Page<Record>>>
    @GET("/api/news/{top}")
    fun getTop(@Path("top") top:String):LiveData<Res<List<Record>>>
    @GET("/api/video/all")
    fun getVideo():LiveData<Res<List<VideoModel>>>
    @GET("/api/sms/sendrcode")
    fun getRegistryCode(@Query("phone") phone:String) :LiveData<Res<Int>>
    @POST("/api/sms/checkrcode")
    fun checkRegistryCode(@Body map: Map<String,String>):LiveData<Res<String?>>
   @POST("/api/user/register")
    fun register(@Body map: Map<String,String>):LiveData<Res<String?>>
    @POST("/api/user/loginname")
    fun login(@Body map: Map<String,String>):LiveData<Res<String>>
    @GET("/api/ud/detail")
    fun getUserDetail():LiveData<Res<User>>
}