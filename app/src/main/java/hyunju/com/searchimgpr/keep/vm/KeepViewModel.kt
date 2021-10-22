package hyunju.com.searchimgpr.keep.vm

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.subjects.PublishSubject

class KeepViewModel : ViewModel() {
    
    val uiEvent = PublishSubject.create<KeepUiEvent>()

    fun showDetail(imgStr: String) {
        uiEvent.onNext(KeepUiEvent.MoveDetail(imgStr))
    }

    fun removeBookmark(imgStr: String) {
        uiEvent.onNext(KeepUiEvent.RemoveBookmark(imgStr))
    }
}

sealed class KeepUiEvent {
    data class MoveDetail(val imgStr: String) : KeepUiEvent()
    data class RemoveBookmark(val imgStr: String): KeepUiEvent()
}