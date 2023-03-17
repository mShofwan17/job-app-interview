package interview.dansmultipro.core.network

sealed class StateResponse<T> {
    data class Success<T>(val data:T?) : StateResponse<T>()
    data class Error<T>(val message:String?, val exception: Throwable?=null) : StateResponse<T>()
    class Loading<T> : StateResponse<T>()
}
