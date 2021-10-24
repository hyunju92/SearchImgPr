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
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.FragmentKeepBinding
import hyunju.com.searchimgpr.detail.view.DetailActivity
import hyunju.com.searchimgpr.keep.vm.KeepUiEvent
import hyunju.com.searchimgpr.keep.vm.KeepViewModel
import hyunju.com.searchimgpr.main.vm.SharedViewModel
import hyunju.com.searchimgpr.util.replaceAll
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
        const val IMG_STR = "imgStr"
        const val IS_MARKED = "isMarked"
        const val SEARCH_DATA = "searchData"
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
        sharedViewModel.keepSearchDataList.observe(viewLifecycleOwner, Observer {
            binding.keepRv.replaceAll(it)
        })
        sharedViewModel.testSetKeepImgList()

    }

    private fun handleUiEvent(uiEvent: KeepUiEvent) = when(uiEvent) {
        is KeepUiEvent.MoveDetail -> moveDetail(uiEvent.imgStr)
        is KeepUiEvent.RemoveBookmark -> removeBookmark(uiEvent.imgStr)
    }

    private fun moveDetail(imgStr : String) {
        Intent(requireActivity(), DetailActivity::class.java).apply {
            putExtra(IMG_STR, imgStr)
            putExtra(IS_MARKED, true)
            //            add("https://search.pstatic.net/common/?src=http%3A%2F%2Fblogfiles.naver.net%2FMjAyMTA5MjZfMjgw%2FMDAxNjMyNjE4MDc5NzE3.CIb_BrZ3n5N-wNLKWi0sJf05T8UXedjoDlyxRhqaMW8g.am87-tm1N34zMW2BsN0hPX_vtIvP_eGnZzeAUluXppwg.JPEG.leeeunhye010118%2F1632618076387.jpg&type=sc960_832")
//            putExtra(SEARCH_DATA, testData)


        }.let {
            startDetail.launch(it)
        }
    }

    private fun startDetailResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val isMarked = result.data?.getBooleanExtra(IS_MARKED, false)
            val imgStr = result.data?.getStringExtra(IMG_STR)

            if(isMarked == false && imgStr != null) removeBookmark(imgStr)
        }
    }

    private fun removeBookmark(imgStr: String) {
        sharedViewModel.removeKeepList(imgStr)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        eventDisposable?.dispose()
    }

}