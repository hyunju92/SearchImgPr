package hyunju.com.searchimgpr.detail.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel

class DetailViewModel : ViewModel(){
    val imgStrValue = ObservableField<String>()

    fun testSetImgStr(imgStr : String) {
        val testUri = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTEwMTFfMjM3%2FMDAxNjMzODk5MzIxNzI5.xSRZ8-Wf0_YJjYLpxlhT5HsL_gmGk1kM3zRdgSn0adgg.efBHUa2U_d5lZ4dgaIVLPyfM0X9K8L92bmON1vPZQhQg.PNG.keunak%2Fimage.png&type=sc960_832"
        imgStrValue.set(imgStr)
    }

}