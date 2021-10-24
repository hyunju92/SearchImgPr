package hyunju.com.searchimgpr.detail.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import hyunju.com.searchimgpr.search.model.SearchData

class DetailViewModel : ViewModel(){
    val searchData = ObservableField<SearchData>()

    fun setImgData(searchData: SearchData) {
        this.searchData.set(searchData)
    }

    fun onBookmarkClicked() {
        val currentIsKept = searchData.get()?.isKept?.get()?:false
        searchData.get()?.isKept?.set(!currentIsKept)
    }
}