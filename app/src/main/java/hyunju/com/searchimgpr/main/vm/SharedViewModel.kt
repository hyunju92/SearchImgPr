package hyunju.com.searchimgpr.main.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import hyunju.com.searchimgpr.search.model.SearchData

class SharedViewModel : ViewModel() {

    private val _keepSearchDataList = MutableLiveData<List<SearchData>>()
    val keepSearchDataList: LiveData<List<SearchData>>
        get() = _keepSearchDataList


    fun testSetKeepImgList() {
//        val testList = mutableListOf<String>().apply {
//            add("https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTEwMTFfMjM3%2FMDAxNjMzODk5MzIxNzI5.xSRZ8-Wf0_YJjYLpxlhT5HsL_gmGk1kM3zRdgSn0adgg.efBHUa2U_d5lZ4dgaIVLPyfM0X9K8L92bmON1vPZQhQg.PNG.keunak%2Fimage.png&type=sc960_832")
//            add("https://postfiles.pstatic.net/MjAyMTA5MjdfODEg/MDAxNjMyNjc5NjU3MTg5.s0noowKoHXssZOPkIELVXez0UVtnj1x5bQL1j3u_IcMg.vipXGD2B6rwYxiJb9U3IMj8uUqozht0kahG74D5zG4og.JPEG.kesu2020/IMG_5468.jpg?type=w773")
//            add("https://postfiles.pstatic.net/MjAyMTA5MjdfMjk2/MDAxNjMyNjc5NjU4MjYz.yY4Wv4ZFoMME5V9I-2jUo6QxHHL97ZeSE0GfYczNQA8g.xFkygFPmF95YPViX0lVnh_YaFhaFdY7uIrDgL035PaAg.JPEG.kesu2020/IMG_5469.jpg?type=w773")
//            add("https://postfiles.pstatic.net/MjAyMTA5MjdfMjM5/MDAxNjMyNjgxMDgwNjEx.CQUDtdFsSwtK38GVRnE7LgiY_50DxeqKSiTtylUCdoEg.Gj0YC19R6p8OzbycwXuqHfIpGpoLyMKI_MliHk3Lieog.GIF.kesu2020/1643263376.gif?type=w773")
//            add("https://postfiles.pstatic.net/MjAyMTA5MjdfOTQg/MDAxNjMyNjc5NjU3OTI0.-8Vi7VzrnxPuMzmGI8Sz3FMOrrY935O1zk6CyaURe_cg.E8lj4i7ytGp3Lrnr85GhRZHnUu_ICwkbNGIY5t8Tb-0g.JPEG.kesu2020/IMG_5483.jpg?type=w773")
//            add("http://blog.naver.com/storyphoto/viewer.jsp?src=https%3A%2F%2Fblogfiles.pstatic.net%2FMjAyMTEwMDdfMTAy%2FMDAxNjMzNTg3NjI2MzQx.e-cfNXOW0k81khmlVj2rysawYw1xNPmThHevRffw7p4g.xBhbGBFqigdeHlojaXWXdw2sfWHQPSdbAWGlmtgUvxsg.PNG.wisdomhouse7%2Fimage.png")
//            add("https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA5MjdfMTUy%2FMDAxNjMyNzUwNDY5ODQw.EtXzg6Q8GJVYayiJKurWFjGZsGWXhT4sYrs0s6ARpTgg.ILzRWJgeDeiykVJIqZjprFVlz9L1HkPlTkGj4bh44-4g.JPEG.xeo1110%2FIMG_1350.jpg&type=sc960_832")
//            add("https://postfiles.pstatic.net/MjAyMTEwMDRfMTg1/MDAxNjMzMzI4MjkwMTQ3._jV4xbOBn8jv5NJdXqXyACwQv76Y4dxtzf3I2VOlOesg.o3HrKmWC8Z6aJO21cYxZcqbvICGljiQmQAPFSN-PjaUg.PNG.1983jinhee/image.png?type=w773")
//            add("https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA5MjZfMjgw%2FMDAxNjMyNjE4MDc5NzE3.CIb_BrZ3n5N-wNLKWi0sJf05T8UXedjoDlyxRhqaMW8g.am87-tm1N34zMW2BsN0hPX_vtIvP_eGnZzeAUluXppwg.JPEG.leeeunhye010118%2F1632618076387.jpg&type=sc960_832")
//        }
//


        val testData = SearchData(
            imgUrl = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA5MjZfMjgw%2FMDAxNjMyNjE4MDc5NzE3.CIb_BrZ3n5N-wNLKWi0sJf05T8UXedjoDlyxRhqaMW8g.am87-tm1N34zMW2BsN0hPX_vtIvP_eGnZzeAUluXppwg.JPEG.leeeunhye010118%2F1632618076387.jpg&type=sc960_832",
            thumbnailUrl = "https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA5MjZfMjgw%2FMDAxNjMyNjE4MDc5NzE3.CIb_BrZ3n5N-wNLKWi0sJf05T8UXedjoDlyxRhqaMW8g.am87-tm1N34zMW2BsN0hPX_vtIvP_eGnZzeAUluXppwg.JPEG.leeeunhye010118%2F1632618076387.jpg&type=sc960_832",
            dateTime = "21211024",
            imgType = "IMG"
        )

        addKeepList(testData)

    }

    fun addKeepList(data: SearchData) {
        val newList = _keepSearchDataList.value?.toMutableList()?.apply { add(data) }?: arrayListOf(data)
        _keepSearchDataList.value = newList

    }

    fun removeKeepList(data: SearchData) {
        _keepSearchDataList.value?.toMutableList()?.apply { remove(data) }?.let { newList ->
            _keepSearchDataList.value = newList
        }
    }
}