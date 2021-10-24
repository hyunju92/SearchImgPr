package hyunju.com.searchimgpr.bookmark.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.bookmark.vm.BookmarkViewModel
import hyunju.com.searchimgpr.databinding.SubviewBookmarkImgBinding
import hyunju.com.searchimgpr.main.vm.SharedViewModel
import hyunju.com.searchimgpr.search.model.SearchData
import hyunju.com.searchimgpr.util.RecyclerAdapter

class BookmarkAdapter(private val bookmarkViewModel: BookmarkViewModel, private val sharedViewModel: SharedViewModel)
    : RecyclerView.Adapter<BookmarkAdapter.BookmarkViewHolder>(), RecyclerAdapter<SearchData>{
    private var searchDataList : ArrayList<SearchData>? = null

    override fun replaceAll(recyclerView: RecyclerView, listItem: List<SearchData>?) {
        listItem?.let { newList ->
            if(searchDataList == null) {
                searchDataList?.clear()
                searchDataList = listItem as ArrayList<SearchData>

                notifyDataSetChanged()

            } else {
                val diffResult = DiffUtil.calculateDiff(BookmarkDiffUtil(searchDataList!!, newList))
                searchDataList!!.clear()
                searchDataList!!.addAll(newList)

                diffResult.dispatchUpdatesTo(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookmarkViewHolder {
        return DataBindingUtil.inflate<SubviewBookmarkImgBinding>(
            LayoutInflater.from(parent.context),
            R.layout.subview_bookmark_img,
            parent,
            false
        ).let {
            it.bookmarkVm = bookmarkViewModel
            it.sharedVm = sharedViewModel
            BookmarkViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: BookmarkViewHolder, position: Int) {
        holder.bind(searchDataList!![position])
    }

    override fun getItemCount(): Int {
        return searchDataList?.size?:0
    }

    class BookmarkViewHolder(private val binding: SubviewBookmarkImgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SearchData) {
            binding.data = data
            binding.imgUri = data.thumbnailUrl
        }
    }

    class BookmarkDiffUtil (private val oldList : List<SearchData>, private val newList: List<SearchData>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].thumbnailUrl == newList[newItemPosition].thumbnailUrl
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

}