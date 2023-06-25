package org.jash.sportsnews.database

import android.content.Context
import org.jash.sportsnews.App

val Context.database:AppDatabase
    get() =(applicationContext as App).appDatabase