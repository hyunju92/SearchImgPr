package hyunju.com.searchimgpr.search.network

import hyunju.com.searchimgpr.search.model.SearchMergedData

// Image Data
data class ReqImageData(val query: String, val sort: String?, val page: Int?, val size: Int?)

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
    val dateTime: String
)


// Vclip Data
data class ReqVclipData(val query: String, val sort: String?, val page: Int?, val size: Int?)

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
fun List<ImageDocuments>.toListSearchUi(): List<SearchMergedData> {
    return map {
        it.toSearchUiData()
    }
}

fun ImageDocuments.toSearchUiData() : SearchMergedData {
    return SearchMergedData(
        thumbnail_url,
        image_url,
        dateTime,
        "IMG"
    )
}

fun VclipDocuments.toSearchUiData() : SearchMergedData {
    return SearchMergedData(
        thumbnail,
        thumbnail,
        datetime,
        "VCLIP"
    )
}



