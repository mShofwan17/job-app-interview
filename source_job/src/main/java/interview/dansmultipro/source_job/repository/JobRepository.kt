package interview.dansmultipro.source_job.repository


import interview.dansmultipro.core.model.Job
import interview.dansmultipro.source_job.repository.IJobRepository
import interview.dansmultipro.source_job.services.JobServices
import okhttp3.MultipartBody
import retrofit2.Response
import javax.inject.Inject

class JobRepository @Inject constructor(
    private val services: JobServices
) : IJobRepository {
    override suspend fun getListJob(page: Int?): Response<List<Job>> {
        return services.getListJob(page)
    }

    override suspend fun getSearchingJob(
        location: String?,
        description: String?,
        full_time: String?
    ): Response<List<Job>> {
        return services.getSearchingJob(location, description, full_time)
    }

    override suspend fun getDetailJob(id: String?): Response<Job> {
        return services.getDetailJob(id)
    }


}