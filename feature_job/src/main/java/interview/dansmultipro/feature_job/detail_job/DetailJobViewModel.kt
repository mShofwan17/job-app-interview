package interview.dansmultipro.feature_job.detail_job

import androidx.lifecycle.viewModelScope
import interview.dansmultipro.core.model.Job
import interview.dansmultipro.core.network.StateResponse
import interview.dansmultipro.source_job.usecase.GetDetailJobUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import interview.dansmultipro.core.base.BaseViewModel
import interview.dansmultipro.core.base.BaseVmFactory
import javax.inject.Inject

class DetailJobViewModel @Inject constructor(
    private val getDetailJobUseCase: GetDetailJobUseCase
) : BaseViewModel() {

    private var _job = MutableStateFlow<Job?>(null)
    val job get() = _job.asStateFlow()

    fun getJobDetail(id:String?){
        viewModelScope.launch {
            getDetailJobUseCase(id)
                .collectLatest {
                    when(it){
                        is StateResponse.Loading -> {
                            setStateLoading(true)
                        }
                        is StateResponse.Success -> {
                            setStateLoading(false)
                            _job.emit(it.data)
                        }
                        is StateResponse.Error -> {
                            setStateLoading(false)
                            it.message?.let { it1 -> setErrorMessage(it1) }
                        }
                    }
                }
        }
    }

    class Factory @Inject constructor(
        getDetailJobUseCase: GetDetailJobUseCase
    ) : BaseVmFactory(
        DetailJobViewModel(
            getDetailJobUseCase)
    )
}