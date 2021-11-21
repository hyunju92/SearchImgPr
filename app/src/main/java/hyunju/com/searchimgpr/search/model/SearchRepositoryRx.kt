package hyunju.com.searchimgpr.search.model

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.observable
import hyunju.com.searchimgpr.search.network.SearchNetworkApiRx
import io.reactivex.Observable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
class SearchRepositoryRx @Inject constructor(
    private val searchNetworkApiApi: SearchNetworkApiRx
) : SearchRepository {
    companion object {
        const val DEFAULT_PAGE_INDEX = 1
        const val DEFAULT_PAGE_SIZE = 20
    }

    override fun loadSearchList(searchText: String): Observable<PagingData<SearchData>> {
        return getPager(searchText).observable
    }

    private fun getPager(searchText: String): Pager<Int, SearchData> {
        return Pager(
            config = PagingConfig(pageSize = DEFAULT_PAGE_SIZE, enablePlaceholders = true),
            pagingSourceFactory = { SearchPagingSourceRx(searchText, searchNetworkApiApi) }
        )
    }

}