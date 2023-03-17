package interview.dansmultipro.source_job.services

import interview.dansmultipro.core.model.Job
import interview.dansmultipro.source_job.constant.JobEndPoint.GET_LIST_JOB
import interview.dansmultipro.source_job.constant.JobEndPoint.GET_DETAIL_JOB
import interview.dansmultipro.source_job.constant.JobEndPoint.GET_SEARCHING_JOB
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface JobServices {

    @GET(GET_LIST_JOB)
    suspend fun getListJob(
        @Query("page") page:Int? = 1
    ) : Response<List<Job>>

    @GET(GET_SEARCHING_JOB)
    suspend fun getSearchingJob(
        @Query("location") location:String?,
        @Query("description") description:String?,
        @Query("full_time") full_time:String? = false.toString()
    ) : Response<List<Job>>


    @GET("$GET_DETAIL_JOB/{id}")
    suspend fun getDetailJob(
        @Path("id") id :String?
    ) : Response<Job>
}