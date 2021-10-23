package hyunju.com.searchimgpr.search.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import hyunju.com.searchimgpr.search.model.SearchRepository
import hyunju.com.searchimgpr.search.model.SearchData
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(private val searchRepository: SearchRepository) : ViewModel(){

    val uiEvent = PublishSubject.create<SearchUiEvent>()

    fun searchText(searchText: String) {
        searchText.let {
            if(it.isNotEmpty() && it.isNotBlank()) {
                uiEvent.onNext(SearchUiEvent.SearchText(searchText))
            }
        }
    }

    fun getSearchList(searchText: String): LiveData<PagingData<SearchData>> {
        return searchRepository
            .loadSearchList(searchText)
            .cachedIn(viewModelScope)
    }
}

sealed class SearchUiEvent {
    data class SearchText(val searchText: String) : SearchUiEvent()
}