package com.turanapps.medicationreminder.Error

import androidx.lifecycle.MutableLiveData

data class Error(
    val errorB: MutableLiveData<Boolean?>,
    var error: String
)