package hyunju.com.searchimgpr.search.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.FragmentSearchBinding
import hyunju.com.searchimgpr.detail.view.DetailActivity
import hyunju.com.searchimgpr.main.vm.SharedViewModel
import hyunju.com.searchimgpr.search.vm.SearchUiEvent
import hyunju.com.searchimgpr.search.vm.SearchViewModel
import io.reactivex.rxjava3.disposables.Disposable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val searchViewModel: SearchViewModel by viewModels()

    private var eventDisposable: Disposable? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<FragmentSearchBinding>(inflater, R.layout.fragment_search, container, false).apply {

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeLiveData()
    }

    private fun initView() {
        binding.searchRv.run {
            val spanCount = resources.getInteger(R.integer.img_list_span_count)
            layoutManager = GridLayoutManager(requireContext(), spanCount)
            adapter = SearchImgAdapter(searchViewModel, sharedViewModel)
        }

        observeSearchList("유미의 세포들")

        binding.searchBtn.setOnClickListener {
            moveDetail()
        }
    }
    private fun observeLiveData() {
        eventDisposable = searchViewModel.uiEvent.subscribe {
            handleUiEvent(it)
        }
    }

    private fun observeSearchList(searchText: String) {
        searchViewModel.getSearchList(searchText).observe(viewLifecycleOwner, Observer {
            lifecycleScope.launch {
                (binding.searchRv.adapter as SearchImgAdapter).submitData(it)
            }
        })

    }

    private fun handleUiEvent(uiEvent: SearchUiEvent?) {

    }

    private fun moveDetail() {
        startActivity(Intent(requireActivity(), DetailActivity::class.java))
    }
}