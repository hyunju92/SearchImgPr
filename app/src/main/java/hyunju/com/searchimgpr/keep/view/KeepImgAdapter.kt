package hyunju.com.searchimgpr.keep.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.SubviewImgBinding
import hyunju.com.searchimgpr.util.RecyclerAdapter

class KeepImgAdapter : RecyclerView.Adapter<KeepImgAdapter.KeepImgViewHolder>(), RecyclerAdapter<String>{
    private var imgList : ArrayList<String>? = null

    override fun replaceAll(recyclerView: RecyclerView, listItem: List<String>?) {
        listItem?.let { newList ->
            if(imgList == null) {
                imgList?.clear()
                imgList = listItem as ArrayList<String>

                notifyDataSetChanged()

            } else {
                imgList!!.clear()
                imgList!!.addAll(newList)

                val diffResult = DiffUtil.calculateDiff(KeepImgDiffUtil(imgList!!, newList))
                diffResult.dispatchUpdatesTo(this)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KeepImgViewHolder {
        return DataBindingUtil.inflate<SubviewImgBinding>(
            LayoutInflater.from(parent.context),
            R.layout.subview_img,
            parent,
            false
        ).let {
            KeepImgViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: KeepImgViewHolder, position: Int) {
        holder.bind(imgList!![position])
    }

    override fun getItemCount(): Int {
        return imgList?.size?:0
    }

    class KeepImgViewHolder(private val binding: SubviewImgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(imgUrl: String) {
            binding.imgUri = imgUrl
        }
    }

    class KeepImgDiffUtil (private val oldList : List<String>, private val newList: List<String>) : DiffUtil.Callback(){
        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return true
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }

}