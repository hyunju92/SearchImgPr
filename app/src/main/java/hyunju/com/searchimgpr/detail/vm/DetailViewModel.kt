package hyunju.com.searchimgpr.detail.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel(){
    val imgStr = ObservableField<String>()
    val isMarked = ObservableField<Boolean>()

    fun setImgData(imgStr: String, isMarked: Boolean) {
        this.imgStr.set(imgStr)
        this.isMarked.set(isMarked)
    }

}