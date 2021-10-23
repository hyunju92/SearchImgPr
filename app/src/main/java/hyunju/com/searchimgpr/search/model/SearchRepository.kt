package hyunju.com.searchimgpr.search.model

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import hyunju.com.searchimgpr.search.network.SearchNetworkApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SearchRepository @Inject constructor(
    private val searchNetworkApi: SearchNetworkApi
) {

    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
    }

    fun loadSearchList(searchText: String): LiveData<PagingData<SearchData>> {
        val pageConfig = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true)

        return Pager(
            config = pageConfig,
            pagingSourceFactory = { SearchPagingSource(searchText, searchNetworkApi) }
        ).liveData
    }

}