package hyunju.com.searchimgpr.bookmark.view

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
import hyunju.com.searchimgpr.detail.view.DetailActivity
import hyunju.com.searchimgpr.bookmark.vm.BookmarkUiEvent
import hyunju.com.searchimgpr.bookmark.vm.BookmarkViewModel
import hyunju.com.searchimgpr.databinding.FragmentBookrmarkBinding
import hyunju.com.searchimgpr.main.vm.SharedViewModel
import hyunju.com.searchimgpr.search.model.SearchData
import io.reactivex.rxjava3.disposables.Disposable

@AndroidEntryPoint
class BookmarkFragment : Fragment() {

    private lateinit var binding: FragmentBookrmarkBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val bookmarkViewModel: BookmarkViewModel by viewModels()
    private var eventDisposable: Disposable? = null

    private val startDetail = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        resultStartDetail(result)
    }

    companion object {
        const val SEARCH_DATA = "SEARCH_DATA"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<FragmentBookrmarkBinding>(inflater, R.layout.fragment_bookrmark, container, false).apply {
            sharedVm = sharedViewModel
            bookmarkVm = bookmarkViewModel
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeLiveData()
    }

    private fun initView() {
        binding.bookmarkRv.run {
            val spanCount = resources.getInteger(R.integer.img_list_span_count)
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = BookmarkAdapter(bookmarkViewModel, sharedViewModel)
        }
    }

    private fun observeLiveData() {
        eventDisposable = bookmarkViewModel.uiEvent.subscribe {
            handleUiEvent(it)
        }
    }

    private fun handleUiEvent(uiEvent: BookmarkUiEvent) = when(uiEvent) {
        is BookmarkUiEvent.MoveDetail -> moveDetail(uiEvent.data)
        is BookmarkUiEvent.ChangeBookmarkState -> sharedViewModel.onClickBookmark(uiEvent.data)
    }

    private fun moveDetail(data : SearchData) {
        Intent(requireActivity(), DetailActivity::class.java).apply {
            putExtra(SEARCH_DATA, data)
        }.let {
            startDetail.launch(it)
        }
    }

    private fun resultStartDetail(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val resultData = result.data?.getParcelableExtra<SearchData>(SEARCH_DATA)
            bookmarkViewModel.resultDetail(resultData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        eventDisposable?.dispose()
    }

}