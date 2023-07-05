package org.jash.profile.model

import androidx.databinding.Observable
import androidx.databinding.Observable.OnPropertyChangedCallback
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import org.jash.mylibrary.logging.logging
import org.jash.mylibrary.model.User
import org.jash.mylibrary.network.service
import org.jash.mylibrary.processor

class UserFollow(var isFollow:ObservableBoolean) {
    init {
        isFollow.addOnPropertyChangedCallback(object :OnPropertyChangedCallback(){
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if(isFollow.get()) {
                    service.follow(user.get()?.id ?: 0).observeForever {
                        processor.onNext(it.msg)
                    }
                } else {
                    service.followDel(user.get()?.id ?: 0).observeForever {
                        processor.onNext(it.msg)
                    }
                }

            }

        })
    }
    val user:ObservableField<User> = ObservableField()

}