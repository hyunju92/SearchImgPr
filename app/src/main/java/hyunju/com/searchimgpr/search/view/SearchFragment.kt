package hyunju.com.searchimgpr.search.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.FragmentSearchBinding
import hyunju.com.searchimgpr.detail.view.DetailActivity
import hyunju.com.searchimgpr.detail.view.DetailActivity.Companion.CLICKED_DATA_FOR_DETAIL
import hyunju.com.searchimgpr.main.vm.SharedViewModel
import hyunju.com.searchimgpr.search.model.SearchData
import hyunju.com.searchimgpr.search.vm.SearchUiEvent
import hyunju.com.searchimgpr.search.vm.SearchViewModel
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    private var eventDisposable: Disposable? = null

    private val startDetail = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        resultStartDetail(result)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<FragmentSearchBinding>(inflater, R.layout.fragment_search, container, false).apply {
            lifecycleOwner = this@SearchFragment
            sharedVm = sharedViewModel
            searchVm = searchViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeData()
    }

    private fun initView() {
        binding.searchRv.run {
            val spanCount = resources.getInteger(R.integer.img_list_span_count)
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = SearchImgAdapter(searchViewModel, sharedViewModel)
        }
    }

    private fun observeData() {

        viewLifecycleOwner.lifecycleScope.launch {
            searchViewModel.searchList.collectLatest {
                (binding.searchRv.adapter as SearchImgAdapter).submitData(it)
            }
        }

//        searchViewModel.searchListByObservable.addOnPropertyChangedCallback(object: Observable.OnPropertyChangedCallback(){
//            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
//                val data = searchViewModel.searchListByObservable.get()?:return
//                (binding.searchRv.adapter as SearchImgAdapter).submitData(lifecycle, data)
//            }
//        })


        eventDisposable = searchViewModel.uiEvent.subscribe {
            handleUiEvent(it)
        }
    }

    private fun handleUiEvent(uiEvent: SearchUiEvent?) = when (uiEvent) {
        is SearchUiEvent.MoveDetail -> moveDetail(uiEvent.data)
        is SearchUiEvent.ChangeBookmarkState -> sharedViewModel.onClickBookmark(uiEvent.data)
        else -> {}
    }

    private fun moveDetail(data: SearchData) {
        Intent(requireActivity(), DetailActivity::class.java).apply {
            putExtra(CLICKED_DATA_FOR_DETAIL, data)
        }.let {
            startDetail.launch(it)
        }
    }

    private fun resultStartDetail(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val resultData = result.data?.getParcelableExtra<SearchData>(CLICKED_DATA_FOR_DETAIL)
            searchViewModel.resultDetail(resultData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        eventDisposable?.dispose()
    }
}