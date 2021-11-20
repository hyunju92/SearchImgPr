package hyunju.com.searchimgpr.search.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import hyunju.com.searchimgpr.search.model.SearchRepository
import hyunju.com.searchimgpr.search.model.SearchData
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel(){

    val uiEvent = PublishSubject.create<SearchUiEvent>()
    private var currentClickedData : SearchData? = null

    // search list
    private val _searchList = MutableStateFlow<PagingData<SearchData>?>(null)
    val searchList : StateFlow<PagingData<SearchData>?> = _searchList

    fun searchText(searchText: String?) {
        searchText?.let {
            if(it.isEmpty() || it.isBlank())return@let

            viewModelScope.launch {
                searchRepository
                    .loadSearchListByFLow(searchText)
                    .cachedIn(viewModelScope)
                    .collect {
                        _searchList.value = it
                    }

            }
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