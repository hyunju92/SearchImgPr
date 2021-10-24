package hyunju.com.searchimgpr.bookmark.vm

import androidx.lifecycle.ViewModel
import hyunju.com.searchimgpr.search.model.SearchData
import io.reactivex.rxjava3.subjects.PublishSubject

class BookmarkViewModel : ViewModel() {
    
    val uiEvent = PublishSubject.create<BookmarkUiEvent>()
    private var currentClickedData : SearchData? = null

    fun showDetail(data: SearchData) {
        currentClickedData = data
        uiEvent.onNext(BookmarkUiEvent.MoveDetail(data))
    }

    fun resultDetail(data: SearchData?) {
        if(data != null && currentClickedData != null
            && data.isKept.get() == false) {
            uiEvent.onNext(BookmarkUiEvent.ChangeBookmarkState(currentClickedData!!))
        }
    }
}

sealed class BookmarkUiEvent {
    data class MoveDetail(val data: SearchData) : BookmarkUiEvent()
    data class ChangeBookmarkState(val data: SearchData) : BookmarkUiEvent()
}