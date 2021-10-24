package hyunju.com.searchimgpr.main.vm

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import hyunju.com.searchimgpr.search.model.SearchData

class SharedViewModel : ViewModel() {

    val bookmarkList = ObservableField<List<SearchData>>()

    fun addBookrmarkList(data: SearchData) {
        val newList = bookmarkList.get()?.toMutableList()?.apply { add(data) }?: arrayListOf(data)
        bookmarkList.set(newList)

        data.isKept.set(true)
    }

    fun removeBookmarkList(data: SearchData) {
        val newList = bookmarkList.get()?.toMutableList()?.apply { removeIf { it.thumbnailUrl == data.thumbnailUrl } }
        newList?.let { bookmarkList.set(newList) }

        data.isKept.set(false)
    }

    fun onClickBookmark(data: SearchData) {
        data.isKept.get()?.let { currentIsKept ->
            if(currentIsKept) {
                removeBookmarkList(data)
            } else {
                addBookrmarkList(data)
            }
        }

    }

    fun isKept(data: SearchData) : Boolean{
        return bookmarkList.get()?.contains(data)?:false
    }

}