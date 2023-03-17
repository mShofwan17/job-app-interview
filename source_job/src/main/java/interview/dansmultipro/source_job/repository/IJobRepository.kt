package interview.dansmultipro.source_job.repository

import interview.dansmultipro.core.model.Job
import okhttp3.MultipartBody
import retrofit2.Response

interface IJobRepository {
    suspend fun getListJob(page:Int?): Response<List<Job>>
    suspend fun getSearchingJob(
        location: String?,
        description: String?,
        full_time: String?
    ): Response<List<Job>>
    suspend fun getDetailJob(
        id : String?
    ) : Response<Job>
}