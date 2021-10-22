package hyunju.com.searchimgpr.keep.vm

import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.subjects.PublishSubject

class KeepViewModel : ViewModel() {
    
    val uiEvent = PublishSubject.create<KeepUiEvent>()

    fun showDetail(imgStr: String) {
        uiEvent.onNext(KeepUiEvent.MoveDetail(imgStr))
    }
}

sealed class KeepUiEvent {
    data class MoveDetail(val imgStr: String) : KeepUiEvent()
}