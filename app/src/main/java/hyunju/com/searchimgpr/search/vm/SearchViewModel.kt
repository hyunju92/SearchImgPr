package hyunju.com.searchimgpr.search.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import hyunju.com.searchimgpr.search.model.corouine.SearchRepositoryCoroutine
import hyunju.com.searchimgpr.search.model.SearchData
import hyunju.com.searchimgpr.search.model.SearchRepository
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor
    (private val searchRepository : SearchRepository) : ViewModel(){

    val uiEvent = PublishSubject.create<SearchUiEvent>()
    private var currentClickedData : SearchData? = null

    // saerch text
    private val searchTextFlow = MutableStateFlow("")

    // search list
    val searchList = searchTextFlow.flatMapLatest { searchText ->   // emit호출 시, data가 흐름
        (searchRepository as SearchRepositoryCoroutine).loadSearchList(searchText)
    }.cachedIn(viewModelScope)

    fun searchText(searchText: String?) {
        searchText?.let { text ->
            if(text.isEmpty() || text.isBlank())return@let
            searchTextFlow.value = searchText
        }
    }

    fun showDetail(data: SearchData) {
        currentClickedData = data
        uiEvent.onNext(SearchUiEvent.MoveDetail(data))
    }

    fun resultDetail(data: SearchData?) {
        if (data != null && currentClickedData != null
            && currentClickedData?.isKept?.get() != data.isKept.get()) {
            uiEvent.onNext(SearchUiEvent.ChangeBookmarkState(currentClickedData!!))
        }
    }

}

sealed class SearchUiEvent {
    data class MoveDetail(val data: SearchData) : SearchUiEvent()
    data class ChangeBookmarkState(val data: SearchData) : SearchUiEvent()

}