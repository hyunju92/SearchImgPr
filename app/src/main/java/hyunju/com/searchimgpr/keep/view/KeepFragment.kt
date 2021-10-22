package hyunju.com.searchimgpr.keep.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.FragmentKeepBinding
import hyunju.com.searchimgpr.detail.view.DetailActivity
import hyunju.com.searchimgpr.keep.vm.KeepUiEvent
import hyunju.com.searchimgpr.keep.vm.KeepViewModel
import hyunju.com.searchimgpr.main.vm.SharedViewModel
import io.reactivex.rxjava3.disposables.Disposable

class KeepFragment : Fragment() {

    private lateinit var binding: FragmentKeepBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val keepViewModel: KeepViewModel by viewModels()
    private var eventDisposable: Disposable? = null

    companion object {
        const val IMG_STR = "imgStr"
        const val IS_MARKED = "isMarked"
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

    override fun onDestroyView() {
        super.onDestroyView()
        eventDisposable?.dispose()
    }

    private fun initView() {
        binding.keepRv.run {
            val spanCount = resources.getInteger(R.integer.keep_img_list_span_count)
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = KeepImgAdapter(keepViewModel, sharedViewModel)
        }

        sharedViewModel.testSetKeepImgList()
    }

    private fun observeLiveData() {
        eventDisposable = keepViewModel.uiEvent.subscribe {
            handleUiEvent(it)
        }
    }

    private fun handleUiEvent(uiEvent: KeepUiEvent) = when(uiEvent) {
        is KeepUiEvent.MoveDetail -> moveDetail(uiEvent.imgStr)
        is KeepUiEvent.RemoveBookmark -> removeBookmark(uiEvent.imgStr)
    }

    private fun moveDetail(imgStr : String) {
        val intent = Intent(requireActivity(), DetailActivity::class.java)
        intent.putExtra(IMG_STR, imgStr)
        intent.putExtra(IS_MARKED, true)
        startActivity(intent)
    }

    private fun removeBookmark(imgStr: String) {
        sharedViewModel.removeImgUri(imgStr)
    }

}