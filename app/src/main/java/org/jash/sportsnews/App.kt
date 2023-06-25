package org.jash.sportsnews

import android.app.Application
import androidx.room.Room
import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.backends.pipeline.Fresco
import org.jash.sportsnews.database.AppDatabase

class App:Application() {
    lateinit var appDatabase: AppDatabase
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        ARouter.init(this)
        appDatabase = Room.databaseBuilder(this, AppDatabase::class.java, "sport")
            .build()
    }
}