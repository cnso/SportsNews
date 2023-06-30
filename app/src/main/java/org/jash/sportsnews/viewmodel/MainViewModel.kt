package org.jash.sportsnews.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import org.jash.mylibrary.model.Res
import org.jash.mylibrary.model.Category

class MainViewModel: ViewModel() {
     var categories:LiveData<Res<List<Category>>>? = null
}