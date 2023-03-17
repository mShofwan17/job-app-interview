package interview.dansmultipro.core.base

import android.util.Log
import interview.dansmultipro.core.network.StateResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import kotlin.coroutines.CoroutineContext

open class BaseUseCase {
   private val errorMessage = "Terjadi Kesalahan"

    suspend fun <T> doFlow(
        coroutineContext: CoroutineContext = Dispatchers.IO,
        block: suspend FlowCollector<StateResponse<T>>.() -> Unit
    ) : Flow<StateResponse<T>> {
        return flow {
            emit(StateResponse.Loading())
            try {
                block.invoke(this)
            } catch (e: Exception) {
                emit(StateResponse.Error(e.message))
            }
        }.flowOn(coroutineContext)
    }

    suspend fun <T> validateResponseData(
        response: Response<T>,
        successResponse: suspend (T) -> Unit,
        errorResponse: suspend (msg: String) -> Unit
    ) {
        if (response.isSuccessful){
            val body = response.body()
            body?.let {
              successResponse.invoke(it)
            } ?: errorResponse.invoke(errorMessage)
        }else{
            errorResponse.invoke(response.message())

            Log.d("HASILNYA_ADALAAHHHH", "invoke: $response")
        }
    }
}