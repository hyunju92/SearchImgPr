package hyunju.com.searchimgpr.search.vm

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import hyunju.com.searchimgpr.search.model.SearchData
import hyunju.com.searchimgpr.search.model.SearchRepository
import hyunju.com.searchimgpr.search.model.rx.SearchRepositoryRx
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxjava3.subjects.PublishSubject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor
    (private val searchRepository : SearchRepository) : ViewModel(){

    val uiEvent = PublishSubject.create<SearchUiEvent>()
    private var currentClickedData : SearchData? = null

    // search list
    private var disposable : Disposable? = null
    val searchList = ObservableField<PagingData<SearchData>>()

    fun searchText(searchText: String?) {
        searchText?.let { text ->
            if(text.isEmpty() || text.isBlank())return@let
            getSearchList(text)
        }
    }

    private fun getSearchList(searchText: String) {
        disposable = (searchRepository as SearchRepositoryRx).loadSearchList(searchText)
            .cachedIn(viewModelScope)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.d("testRxPaging", "getSearchList: init")
                searchList.set(it)
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


    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }
}

sealed class SearchUiEvent {
    data class MoveDetail(val data: SearchData) : SearchUiEvent()
    data class ChangeBookmarkState(val data: SearchData) : SearchUiEvent()

}