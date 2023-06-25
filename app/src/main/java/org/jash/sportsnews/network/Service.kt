package org.jash.sportsnews.network

import androidx.lifecycle.LiveData
import org.jash.mylibrary.model.Res
import org.jash.sportsnews.model.Category
import org.jash.sportsnews.model.Page
import org.jash.sportsnews.model.Record
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    @GET("/api/nt/all")
    fun getCategory():LiveData<Res<List<Category>>>
    @GET("/api/news/page")
    fun getRecord(@Query("type") type:Int, @Query("page") page:Int, @Query("size") size:Int):LiveData<Res<Page<Record>>>
}