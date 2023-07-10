package org.jash.mylibrary.network

import androidx.lifecycle.LiveData
import org.jash.mylibrary.model.Res
import org.jash.mylibrary.model.Category
import org.jash.mylibrary.model.Comment
import org.jash.mylibrary.model.Page
import org.jash.mylibrary.model.Record
import org.jash.mylibrary.model.User
import org.jash.mylibrary.model.VideoModel
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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
    @GET("/api/user/all")
    fun getAllUser():LiveData<Res<List<User>>>
    @GET("/api/newsComment/comments")
    fun getCommentByNid(@Query("nid") nid:Int):LiveData<Res<List<Comment>>>
    @GET("/api/newsComment/all.do")
    fun getCommentAll():LiveData<Res<List<Comment>>>
    @POST("/api/newsComment/save")
    fun savaComment(@Body map:Map<String, String>):LiveData<Res<String?>>
    @GET("/api/newsCollect/collect")
    fun collect(@Query("nid") id:Int):LiveData<Res<String>>
    @GET("/api/newsCollect/all.do")
    fun getCollectAll():LiveData<Res<List<Map<String, String>>>>
    @GET("/api/ud/detail")
    fun getMyDetail():LiveData<Res<User>>
    @GET("/api/newsCollect/my")
    fun getNewsCollect():LiveData<Res<List<Record>>>
    @GET("/api/uf/num")
    fun getFans():LiveData<Res<Map<String, String>>>
    @GET("/api/uf/all")
    fun getFollowsAll():LiveData<Res<List<Map<String, String>>>>
    @POST("/api/uf/add")
    @FormUrlEncoded
    fun follow(@Field("fuid") fuid:Int):LiveData<Res<String?>>
    @GET("/api/uf/del")
    fun followDel(@Query("fuid") fuid:Int):LiveData<Res<String?>>
    @GET("/api/user/loginout")
    fun logout():LiveData<Res<String?>>
    @POST("/api/user/updatepass")
    @FormUrlEncoded
    fun updatePassword(@Field("password") password:String):LiveData<Res<String?>>
    @POST("/api/ud/update")
    fun updateProfile(@Body user: User?):LiveData<Res<Any?>>
}