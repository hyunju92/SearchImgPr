package hyunju.com.searchimgpr.keep.view

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
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.FragmentKeepBinding
import hyunju.com.searchimgpr.detail.view.DetailActivity
import hyunju.com.searchimgpr.keep.vm.KeepUiEvent
import hyunju.com.searchimgpr.keep.vm.KeepViewModel
import hyunju.com.searchimgpr.main.vm.SharedViewModel
import hyunju.com.searchimgpr.search.model.SearchData
import io.reactivex.rxjava3.disposables.Disposable

@AndroidEntryPoint
class KeepFragment : Fragment() {

    private lateinit var binding: FragmentKeepBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val keepViewModel: KeepViewModel by viewModels()
    private var eventDisposable: Disposable? = null

    private val startDetail = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        startDetailResult(result)
    }

    companion object {
        const val SEARCH_DATA = "SEARCH_DATA"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<FragmentKeepBinding>(inflater, R.layout.fragment_keep, container, false).apply {
            sharedVm = sharedViewModel
            keepVm = keepViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeLiveData()
    }

    private fun initView() {
        binding.keepRv.run {
            val spanCount = resources.getInteger(R.integer.img_list_span_count)
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = KeepAdapter(keepViewModel, sharedViewModel)
        }
    }

    private fun observeLiveData() {
        eventDisposable = keepViewModel.uiEvent.subscribe {
            handleUiEvent(it)
        }
        sharedViewModel.testSetKeepImgList()

    }

    private fun handleUiEvent(uiEvent: KeepUiEvent) = when(uiEvent) {
        is KeepUiEvent.MoveDetail -> moveDetail(uiEvent.data)
    }

    private fun moveDetail(data : SearchData) {
        Intent(requireActivity(), DetailActivity::class.java).apply {
            putExtra(SEARCH_DATA, data)

        }.let {
            startDetail.launch(it)
        }
    }

    private fun startDetailResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val searchData = result.data?.getParcelableExtra<SearchData>(SEARCH_DATA)

            if(searchData?.isKept?.get() == false) sharedViewModel.removeKeepList(searchData)

        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        eventDisposable?.dispose()
    }

}