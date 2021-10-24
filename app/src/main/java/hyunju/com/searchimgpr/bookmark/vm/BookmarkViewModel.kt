package hyunju.com.searchimgpr.bookmark.vm

import androidx.lifecycle.ViewModel
import hyunju.com.searchimgpr.search.model.SearchData
import io.reactivex.rxjava3.subjects.PublishSubject

class BookmarkViewModel : ViewModel() {
    
    val uiEvent = PublishSubject.create<BookmarkUiEvent>()

    fun showDetail(data: SearchData) {
        uiEvent.onNext(BookmarkUiEvent.MoveDetail(data))
    }
}

sealed class BookmarkUiEvent {
    data class MoveDetail(val data: SearchData) : BookmarkUiEvent()
}