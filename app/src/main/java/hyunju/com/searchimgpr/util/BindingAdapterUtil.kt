package hyunju.com.searchimgpr.util

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import hyunju.com.searchimgpr.R

@BindingAdapter("matchHeightToDeviceWidthDivCount")
fun matchHeightToDeviceWidthDivCount(view: View, boolean: Boolean) {
    val spanCount = view.context.resources.getInteger(R.integer.img_list_span_count)

    val pxWidth = view.resources.displayMetrics.widthPixels
    val layoutParams: ViewGroup.LayoutParams = view.layoutParams
    layoutParams.height = pxWidth / spanCount
    view.layoutParams = layoutParams
}


@BindingAdapter("setOnLongClick")
fun setOnLongClick(view: View, func: () -> Unit) {
    view.setOnLongClickListener {
        func()
        return@setOnLongClickListener true
    }
}

interface RecyclerAdapter<T> {
    fun replaceAll(recyclerView: RecyclerView, listItem: List<T>?)
}

@BindingAdapter("replaceAll")
fun <T> RecyclerView.replaceAll(listItem: List<T>?) {
    (this.adapter as? RecyclerAdapter<T>)?.replaceAll(this, listItem)
}

@BindingAdapter("setImgUri")
fun setImgUri(imageView: ImageView, uri: String?) {
    val loadImg = if (uri.isNullOrEmpty()) R.drawable.ic_baseline_error_outline_24 else uri
    val errorImg = R.drawable.ic_baseline_error_outline_24
    val placeholderImg = R.drawable.ic_baseline_collections_8

        Glide.with(imageView.rootView.context)
            .load(loadImg)
            .placeholder(placeholderImg)
            .error(errorImg)
            .into(imageView)
}

@BindingAdapter("selected")
fun selected(view: View, selected: Boolean) {
    view.isSelected = selected
}
