package hyunju.com.searchimgpr.keep.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.SubviewKeepImgBinding
import hyunju.com.searchimgpr.keep.vm.KeepViewModel
import hyunju.com.searchimgpr.main.vm.SharedViewModel
import hyunju.com.searchimgpr.search.model.SearchData
import hyunju.com.searchimgpr.util.RecyclerAdapter

class KeepAdapter(private val keepViewModel: KeepViewModel, private val sharedViewModel: SharedViewModel)
    : RecyclerView.Adapter<KeepAdapter.KeepImgViewHolder>(), RecyclerAdapter<SearchData>{
    private var searchDataList : ArrayList<SearchData>? = null

    override fun replaceAll(recyclerView: RecyclerView, listItem: List<SearchData>?) {
        listItem?.let { newList ->
            if(searchDataList == null) {
                searchDataList?.clear()
                searchDataList = listItem as ArrayList<SearchData>

                notifyDataSetChanged()

            } else {
                val diffResult = DiffUtil.calculateDiff(KeepImgDiffUtil(searchDataList!!, newList))
                searchDataList!!.clear()
                searchDataList!!.addAll(newList)

                diffResult.dispatchUpdatesTo(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeepImgViewHolder {
        return DataBindingUtil.inflate<SubviewKeepImgBinding>(
            LayoutInflater.from(parent.context),
            R.layout.subview_keep_img,
            parent,
            false
        ).let {
            it.keepVm = keepViewModel
            it.sharedVm = sharedViewModel
            KeepImgViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: KeepImgViewHolder, position: Int) {
        holder.bind(searchDataList!![position])
    }

    override fun getItemCount(): Int {
        return searchDataList?.size?:0
    }

    class KeepImgViewHolder(private val binding: SubviewKeepImgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: SearchData) {
            binding.data = data
            binding.imgUri = data.thumbnailUrl
        }
    }

    class KeepImgDiffUtil (private val oldList : List<SearchData>, private val newList: List<SearchData>) : DiffUtil.Callback(){
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