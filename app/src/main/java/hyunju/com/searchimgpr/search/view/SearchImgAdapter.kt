package hyunju.com.searchimgpr.search.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.SubviewSearchImgBinding
import hyunju.com.searchimgpr.main.vm.SharedViewModel
import hyunju.com.searchimgpr.search.model.SearchData
import hyunju.com.searchimgpr.search.vm.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
class SearchImgAdapter(private val searchViewModel: SearchViewModel, private val sharedViewModel: SharedViewModel) :
    PagingDataAdapter<SearchData, SearchImgAdapter.SearchImgViewHolder>(SEARCH_IMG_DIFFUTIL){

    companion object {
        private val SEARCH_IMG_DIFFUTIL = object : DiffUtil.ItemCallback<SearchData>() {
            override fun areItemsTheSame(oldItem: SearchData, newItem: SearchData): Boolean {
                return oldItem.imgUrl == newItem.imgUrl
            }

            override fun areContentsTheSame(oldItem: SearchData, newItem: SearchData): Boolean {
                return oldItem == newItem
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchImgViewHolder {
        return DataBindingUtil.inflate<SubviewSearchImgBinding>(
            LayoutInflater.from(parent.context),
            R.layout.subview_search_img,
            parent,
            false
        ).let {
            it.searchVm = searchViewModel
            it.sharedVm = sharedViewModel
            SearchImgViewHolder(it)
        }
    }

    override fun onBindViewHolder(holder: SearchImgViewHolder, position: Int) {
        getItem(position)?.let { currentItem ->
            holder.bind(currentItem)
        }
    }

    class SearchImgViewHolder(private val binding: SubviewSearchImgBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data : SearchData?) {
            binding.imgUri = data?.imgUrl?:""
        }
    }



}