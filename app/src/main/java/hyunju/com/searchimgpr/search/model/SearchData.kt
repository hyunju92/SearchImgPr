package hyunju.com.searchimgpr.search.model

import android.os.Parcelable
import androidx.databinding.ObservableField
import kotlinx.android.parcel.Parcelize

@Parcelize
data class SearchData(
    val thumbnailUrl: String,
    val imgUrl: String,
    val dateTime: String,
    val imgType: String,
    val isKept: ObservableField<Boolean>
) : Parcelable {

}