package hyunju.com.searchimgpr.detail.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import hyunju.com.searchimgpr.search.model.SearchData

class DetailViewModel : ViewModel(){
    val isMarked = ObservableField<Boolean>()
    val searchData = ObservableField<SearchData>()

    fun setImgData(isMarked: Boolean, searchData: SearchData) {
        this.isMarked.set(isMarked)
        this.searchData.set(searchData)
    }

    fun onBookmarkClicked() {
        val newIsMarked = !(isMarked.get()?:false)
        isMarked.set(newIsMarked)
    }
}