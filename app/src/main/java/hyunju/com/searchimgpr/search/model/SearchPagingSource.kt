package hyunju.com.searchimgpr.search.model

import android.annotation.SuppressLint
import androidx.paging.PagingSource
import androidx.paging.PagingState
import hyunju.com.searchimgpr.search.model.SearchRepository.Companion.DEFAULT_PAGE_INDEX
import hyunju.com.searchimgpr.search.model.SearchRepository.Companion.DEFAULT_PAGE_SIZE
import hyunju.com.searchimgpr.search.network.SearchNetworkApi
import hyunju.com.searchimgpr.search.network.toListSearchUi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

@ExperimentalCoroutinesApi
class SearchPagingSource(private val searchText: String, private val searchNetworkApi: SearchNetworkApi) :
    PagingSource<Int, SearchData>() {

    @SuppressLint("CheckResult")
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, SearchData> {
        val page = params.key ?: DEFAULT_PAGE_INDEX

        return try {
            val reqParam: Map<String, String> = hashMapOf("page" to page.toString(),"query" to searchText, "size" to DEFAULT_PAGE_SIZE.toString())
            val response = searchNetworkApi.getImageData(reqParam)
            val body = response.body()!!.documents.toListSearchUi()

            LoadResult.Page(
                body,
                if (page == DEFAULT_PAGE_INDEX) null else page - 1,
                if (body.isEmpty()) null else page + 1
            )


        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, SearchData>): Int? {
        return null
    }
}