package org.jash.mylibrary

import android.app.Application
import androidx.room.Room
//import com.alibaba.android.arouter.launcher.ARouter
import com.facebook.drawee.backends.pipeline.Fresco
import com.uuzuche.lib_zxing.activity.ZXingLibrary
import org.jash.mylibrary.database.AppDatabase

class App:Application() {
    lateinit var appDatabase: AppDatabase
    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
//        ARouter.openDebug()
//        ARouter.openLog()
//        ARouter.init(this)
        ZXingLibrary.initDisplayOpinion(this)
        appDatabase = Room.databaseBuilder(this, AppDatabase::class.java, "sport")
            .build()
    }
}