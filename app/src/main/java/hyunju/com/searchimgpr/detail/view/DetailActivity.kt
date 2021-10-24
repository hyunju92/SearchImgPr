package hyunju.com.searchimgpr.detail.view

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.ActivityDetailBinding
import hyunju.com.searchimgpr.detail.vm.DetailViewModel
import hyunju.com.searchimgpr.bookmark.view.BookmarkFragment.Companion.SEARCH_DATA
import hyunju.com.searchimgpr.detail.vm.DetailUiEvent
import hyunju.com.searchimgpr.search.model.SearchData
import io.reactivex.rxjava3.disposables.Disposable

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    private var eventDisposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail).apply {
            vm = detailViewModel
        }
        observeLiveData()
        initData()
    }

    private fun observeLiveData() {
        eventDisposable = detailViewModel.uiEvent.subscribe {
            handleUiEvent(it)
        }
    }

    private fun handleUiEvent(uiEvent: DetailUiEvent?) = when (uiEvent) {
        is DetailUiEvent.OpenLinkUrl -> openLinkUrl(uiEvent.linkUrl)
        else -> {}
    }

    private fun initData() {
        val searchData = intent.getParcelableExtra<SearchData>(SEARCH_DATA)
        if(searchData!=null) detailViewModel.setImgData(searchData)
    }

    private fun openLinkUrl(linkUrl: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl)).run {
            startActivity(this)
        }
    }

    override fun onBackPressed() {
        Intent().apply {
            putExtra(SEARCH_DATA, detailViewModel.searchData.get())
        }.let {
            setResult(Activity.RESULT_OK, it)
        }

        super.onBackPressed()
    }


    override fun onDestroy() {
        super.onDestroy()
        eventDisposable?.dispose()
    }

}