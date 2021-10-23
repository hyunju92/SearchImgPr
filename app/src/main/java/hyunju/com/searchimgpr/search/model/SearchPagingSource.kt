package hyunju.com.searchimgpr.search.model

import android.annotation.SuppressLint
import androidx.paging.PagingSource
import androidx.paging.PagingState
import hyunju.com.searchimgpr.search.model.SearchRepository.Companion.DEFAULT_PAGE_INDEX
import hyunju.com.searchimgpr.search.model.SearchRepository.Companion.DEFAULT_PAGE_SIZE
import hyunju.com.searchimgpr.search.network.SearchNetworkApi
import hyunju.com.searchimgpr.search.network.compareDateStringForDec
import hyunju.com.searchimgpr.search.network.imgToListSearchUi
import hyunju.com.searchimgpr.search.network.vclipToListSearchUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

@ExperimentalCoroutinesApi
class SearchPagingSource(
    private val searchText: String,
    private val searchNetworkApi: SearchNetworkApi
) : PagingSource<Int, SearchData>() {

    @SuppressLint("CheckResult")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchData> {
        val page = params.key ?: DEFAULT_PAGE_INDEX

        return try {
            val reqParam = getReqParam(searchText, page)

            val imgRes = searchNetworkApi.getImageData(reqParam).body()!!
                .documents.imgToListSearchUi()
            val vclipRes = searchNetworkApi.getVclipData(reqParam).body()!!
                .documents.vclipToListSearchUi()

            val joinedList = (imgRes + vclipRes).sortedWith { a, b ->
                a.dateTime.compareDateStringForDec(b.dateTime)
            }

            LoadResult.Page(
                joinedList,
                if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                if (joinedList.isEmpty()) null else page + 1
            )

        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    private fun getReqParam(searchText: String, page: Int) : Map<String,String>{
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