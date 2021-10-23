package hyunju.com.searchimgpr.search.network

import android.os.Build
import hyunju.com.searchimgpr.search.model.SearchData
import java.text.SimpleDateFormat
import java.util.*

// ResData
// Image Data
data class ResImageData(val meta: Meta, val documents: List<ImageDocuments>)

data class Meta(val total_count: Int, val pageable_count: Int, val is_end: Boolean)

data class ImageDocuments(
    val collection: String,
    val thumbnail_url: String,
    val image_url: String,
    val width: Int,
    val height: Int,
    val display_sitename: String,
    val doc_url: String,
    val datetime: String
)

// Vclip Data
data class ResVclipData(val meta: Meta, val documents: List<VclipDocuments>)

data class VclipDocuments(
    val title: String,
    val url: String,
    val datetime: String,
    val play_time: Int,
    val thumbnail: String,
    val author: String
)

// mapper
fun List<ImageDocuments>.imgToListSearchUi(): List<SearchData> {
    return map {
        SearchData(
            it.thumbnail_url,
            it.image_url,
            it.datetime,
            "IMG"
        )
    }
}

fun List<VclipDocuments>.vclipToListSearchUi(): List<SearchData> {
    return map {
        SearchData(
            it.thumbnail,
            it.url,
            it.datetime,
            "IMG"
        )
    }
}

fun String.compareDateStringForDec(otherDateString: String) : Int{
    val date1 = this.dateStringtoDate()
    val date2 = otherDateString.dateStringtoDate()

    return if(date1.before(date2)) {
        1
    } else {
        -1
    }
}

fun String.dateStringtoDate(): Date {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX").parse(this)
    } else {
        val newString = this.substring(0, this.length - 6)
        SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(newString)
    }
}





