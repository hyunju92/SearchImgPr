package hyunju.com.searchimgpr.search.model.rx

import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import hyunju.com.searchimgpr.search.model.SearchData
import hyunju.com.searchimgpr.search.model.rx.SearchRepositoryRx.Companion.DEFAULT_PAGE_INDEX
import hyunju.com.searchimgpr.search.model.rx.SearchRepositoryRx.Companion.DEFAULT_PAGE_SIZE
import hyunju.com.searchimgpr.search.network.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SearchPagingSourceRx(
    private val searchText: String,
    private val searchNetworkApiApi: SearchNetworkApiRx
) : RxPagingSource<Int, SearchData>() {


    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, SearchData>> {

        val page = params.key ?: DEFAULT_PAGE_INDEX

        val reqParam = getReqParam(searchText, page)

        val imgRes = searchNetworkApiApi.getImageDataByObservable(reqParam)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map {
                it.documents.imgToListSearchUi()
            }.flatMap { list ->
                Observable.fromIterable(list)
            }

        val vclipRes = searchNetworkApiApi.getVclipDataByObservable(reqParam)
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .map {
                it.documents.vclipToListSearchUi()
            }.flatMap { list ->
                Observable.fromIterable(list)
            }


        return Observable.merge(imgRes, vclipRes)
            .toSortedList { searchData, searchData2 ->
                -searchData.dateTime.compareDateStringForDec(searchData2.dateTime)
            }.map { data ->
                LoadResult.Page(
                    data,
                    if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                    if (data.isNullOrEmpty()) null else page + 1
                )
            }
    }


    private fun getReqParam(searchText: String, page: Int): Map<String, String> {
        return hashMapOf(
            "query" to searchText,
//            "sort" to "recency",
            "page" to page.toString(),
            "size" to (DEFAULT_PAGE_SIZE / 2).toString()
        )
    }

    override fun getRefreshKey(state: PagingState<Int, SearchData>): Int? {
        return null
    }


}