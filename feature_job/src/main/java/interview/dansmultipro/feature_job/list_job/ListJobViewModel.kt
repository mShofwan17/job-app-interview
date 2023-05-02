package interview.dansmultipro.feature_job.list_job

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import interview.dansmultipro.core.model.Job
import interview.dansmultipro.core.network.StateResponse
import interview.dansmultipro.source_job.usecase.GetListJobUseCase
import interview.dansmultipro.source_job.usecase.GetSearchingJobUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import interview.dansmultipro.core.base.BaseViewModel
import interview.dansmultipro.core.base.BaseVmFactory
import interview.dansmultipro.source_job.repository.IJobRepository
import javax.inject.Inject

class ListJobViewModel @Inject constructor(
    private val getListJobUseCase: GetListJobUseCase,
    private val getSearchingJobUseCase: GetSearchingJobUseCase
) : BaseViewModel() {

    private var _listJob = MutableStateFlow<PagingData<Job>?>(null)
    val listJob get() = _listJob.asStateFlow()

    private var _searchJob = MutableStateFlow<List<Job>?>(null)
    val searchJob get() = _searchJob.asStateFlow()

     fun getListJob(){
        viewModelScope.launch {
            getListJobUseCase().cachedIn(this)
                .catch { it.message?.let { it1 -> setErrorMessage(it1) } }
                .collectLatest {
                    _listJob.emit(it)
                }
        }
    }

    fun getSearchingJob(
        location: String? = "",
        description: String? = "",
        full_time: Boolean? = false
    ){
        viewModelScope.launch {
            getSearchingJobUseCase(
                location,
                description,
                full_time
            ).collectLatest {
                when(it){
                    is StateResponse.Loading -> {
                        setStateLoading(true)
                    }
                    is StateResponse.Success -> {
                        setStateLoading(false)
                        _searchJob.emit(it.data)
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
        getListJobUseCase: GetListJobUseCase,
        getSearchingJobUseCase: GetSearchingJobUseCase
    ) : BaseVmFactory(ListJobViewModel(
        getListJobUseCase,
        getSearchingJobUseCase)
    )
}