package interview.dansmultipro.core.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel : ViewModel() {
    private var _loading = MutableLiveData<Boolean?>(null)
    val loading get() = _loading

    private var _errorMsg = MutableLiveData<String?>(null)
    val errorMsg get() = _errorMsg

    fun setStateLoading(boolean: Boolean) {
        _loading.value = boolean
    }

    fun setErrorMessage(message:String?){
        _errorMsg.value = message
    }
}