package com.example.albumsearch.viewmodel

import androidx.lifecycle.ViewModel
import com.example.albumsearch.util.SingleLiveEvent

/** Base [ViewModel] with common functionality */
open class BaseViewModel : ViewModel() {

    /** Id of the string resource to be shown as a toast */
    val toastMessage = SingleLiveEvent<Int>()

}