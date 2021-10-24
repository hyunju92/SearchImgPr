package hyunju.com.searchimgpr.keep.vm

import androidx.lifecycle.ViewModel
import hyunju.com.searchimgpr.search.model.SearchData
import io.reactivex.rxjava3.subjects.PublishSubject

class KeepViewModel : ViewModel() {
    
    val uiEvent = PublishSubject.create<KeepUiEvent>()

    fun showDetail(data: SearchData) {
        uiEvent.onNext(KeepUiEvent.MoveDetail(data))
    }
}

sealed class KeepUiEvent {
    data class MoveDetail(val data: SearchData) : KeepUiEvent()
}