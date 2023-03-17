package interview.dansmultipro.source_job.usecase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import interview.dansmultipro.core.model.Job
import interview.dansmultipro.core.paging.BasePagingSource
import interview.dansmultipro.source_job.repository.IJobRepository
import kotlinx.coroutines.flow.Flow
import interview.dansmultipro.core.base.BaseUseCase
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetListJobUseCase @Inject constructor(
    private val repository: IJobRepository
) : BaseUseCase() {
     suspend operator fun invoke() : Flow<PagingData<Job>>{
         return Pager(
             config = PagingConfig(
                 pageSize = 20,
                 enablePlaceholders = true
             ),

             pagingSourceFactory = {
                 BasePagingSource(
                     loadResult = { params ->
                         val pageIndex = params.key ?: 1
                         try {
                             val response = repository.getListJob(
                                 pageIndex
                             )

                             val resultList = response.body()
                             PagingSource.LoadResult.Page(
                                 data = resultList.orEmpty(),
                                 prevKey = if (pageIndex==1) null else pageIndex -1,
                                 nextKey = if (resultList.isNullOrEmpty()) null else pageIndex +1
                             )
                         } catch (e: IOException) {
                             PagingSource.LoadResult.Error(e)
                         } catch (e: HttpException) {
                             PagingSource.LoadResult.Error(e)
                         }
                     }
                 )
             }
         ).flow
    }
}