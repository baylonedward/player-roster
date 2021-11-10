package com.baylonedward.player_roster.features.trainings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TrainingsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Coming Soon"
    }
    val text: LiveData<String> = _text
}