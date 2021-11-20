package hyunju.com.searchimgpr.search.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import hyunju.com.searchimgpr.search.network.SearchNetworkApi
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SearchRepository @Inject constructor(
    private val searchNetworkApi: SearchNetworkApi
) {
    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
    }

    fun loadSearchListByFLow(searchText: String): Flow<PagingData<SearchData>> {
        return getPager(searchText).flow
    }

    fun loadSearchListByObservable(searchText: String): Observable<PagingData<SearchData>> {
        return getPager(searchText).observable
    }

    private fun getPager(searchText: String): Pager<Int, SearchData> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = { SearchPagingSource(searchText, searchNetworkApi) }
        )
    }


}