package interview.dansmultipro.core.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState

 class BasePagingSource<T : Any>(
   private val loadResult : suspend (params : LoadParams<Int>) -> LoadResult<Int,T>
) : PagingSource<Int, T>() {
    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return loadResult(params)
    }

}