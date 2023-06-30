package org.jash.mylibrary.database

import android.content.Context
import org.jash.mylibrary.App

val Context.database: AppDatabase
    get() =(applicationContext as App).appDatabase