package interview.dansmultipro.source_job.usecase

import interview.dansmultipro.core.model.Job
import interview.dansmultipro.core.network.StateResponse
import interview.dansmultipro.source_job.repository.IJobRepository
import kotlinx.coroutines.flow.Flow
import interview.dansmultipro.core.base.BaseUseCase
import javax.inject.Inject

class GetDetailJobUseCase @Inject constructor(
    private val repository: IJobRepository
) : BaseUseCase() {
     suspend operator fun invoke(id : String?) : Flow<StateResponse<Job?>>{
        return doFlow {
            val response = repository.getDetailJob(id)
            validateResponseData(
                response,
                successResponse = {
                    emit(StateResponse.Success(it))
                },
                errorResponse = {
                    emit(StateResponse.Error(it))
                }
            )
        }
    }
}