package interview.dansmultipro.source_job.usecase

import interview.dansmultipro.core.model.Job
import interview.dansmultipro.core.network.StateResponse
import interview.dansmultipro.source_job.repository.IJobRepository
import kotlinx.coroutines.flow.Flow
import interview.dansmultipro.core.base.BaseUseCase
import javax.inject.Inject

class GetSearchingJobUseCase @Inject constructor(
    private val repository: IJobRepository
) : BaseUseCase() {
     suspend operator fun invoke(
         location: String?,
         description: String?,
         full_time: Boolean?
     ) : Flow<StateResponse<List<Job>>>{
         return doFlow {
             val response = repository.getSearchingJob(
                 location,
                 description,
                 full_time.toString()
             )
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