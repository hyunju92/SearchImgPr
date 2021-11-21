package hyunju.com.searchimgpr.search.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import hyunju.com.searchimgpr.search.network.SearchNetworkApiCoroutine
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SearchRepositoryCoroutine @Inject constructor(
    private val searchNetworkApiCoroutine: SearchNetworkApiCoroutine
) : SearchRepository {
    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
    }

    override fun loadSearchList(searchText: String): Flow<PagingData<SearchData>> {
        return getPager(searchText).flow
    }

    private fun getPager(searchText: String): Pager<Int, SearchData> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = { SearchPagingSourceCoroutine(searchText, searchNetworkApiCoroutine) }
        )
    }

}