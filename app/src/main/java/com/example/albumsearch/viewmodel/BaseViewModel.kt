package com.example.albumsearch.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Base [ViewModel] with common functionality
 *
 */
open class BaseViewModel : ViewModel() {

    /**
     * Id of the string resource to be shown as a toast
     */
    protected val _toastMessage = MutableLiveData<Int?>()
    val toastMessage: LiveData<Int?>
        get() = _toastMessage

    /**
     * Notifies the ViewModel that the toast was shown
     */
    fun onToashShown() {
        _toastMessage.value = null
    }
}