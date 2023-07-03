package org.jash.sportsnews.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import org.jash.mylibrary.model.Comment
import org.jash.mylibrary.network.service
import org.jash.mylibrary.processor
import org.jash.sportsnews.BR

class Detail(val id:Int) :BaseObservable(){
    var up:Boolean = false
        set(value) {
            field = value
            upNumber += if (field) 1 else -1
        }
    var collect:Boolean = false
        set(value) {
            field = value
            service.collect(id).observeForever {
                if (it.code == 0) {
                    processor.onNext(it.data)
                } else {
                    processor.onNext(it.msg)
                }
            }
            collectNumber += if (field) 1 else -1
        }
    @Bindable
    var upNumber: Int = 234
        set(value) {
            field = value
            notifyPropertyChanged(BR.upNumber)
        }
    @Bindable
    var collectNumber :Int = 200
        set(value) {
            field = value
            notifyPropertyChanged(BR.collectNumber)
        }
    @Bindable
    var commentNumber :Int = 200
        set(value) {
            field = value
            notifyPropertyChanged(BR.commentNumber)
        }
    @Bindable
    var comment:String = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.comment)
        }
    @Bindable
    var parent:Comment? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.parent)
        }
    fun submitComment() {

    }
}