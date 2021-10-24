package hyunju.com.searchimgpr.detail.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import hyunju.com.searchimgpr.search.model.SearchData
import io.reactivex.rxjava3.subjects.PublishSubject

class DetailViewModel : ViewModel(){
    val searchData = ObservableField<SearchData>()
    val uiEvent = PublishSubject.create<DetailUiEvent>()

    fun setImgData(searchData: SearchData) {
        this.searchData.set(searchData)
    }

    fun onBookmarkClicked() {
        val currentIsKept = searchData.get()?.isKept?.get()?:false
        searchData.get()?.isKept?.set(!currentIsKept)
    }

    fun imgClicked(linkUrl: String?) {
        linkUrl?.let {
            uiEvent.onNext(DetailUiEvent.OpenLinkUrl(linkUrl))
        }
    }

    fun onBackpressedWithData() {
        searchData.get()?.let {
            uiEvent.onNext(DetailUiEvent.SetResultWithData(it))
        }
    }
}

sealed class DetailUiEvent {
    data class OpenLinkUrl(val linkUrl: String) : DetailUiEvent()
    data class SetResultWithData(val data: SearchData) : DetailUiEvent()
}