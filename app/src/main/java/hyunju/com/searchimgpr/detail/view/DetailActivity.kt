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
import hyunju.com.searchimgpr.detail.vm.DetailUiEvent
import hyunju.com.searchimgpr.search.model.SearchData
import io.reactivex.rxjava3.disposables.Disposable

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    private var eventDisposable: Disposable? = null

    companion object {
        const val CLICKED_DATA_FOR_DETAIL = "CLICKED_DATA_FOR_DETAIL"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail).apply {
            vm = detailViewModel
        }
        observeData()
        initData()
    }

    private fun observeData() {
        eventDisposable = detailViewModel.uiEvent.subscribe {
            handleUiEvent(it)
        }
    }

    private fun handleUiEvent(uiEvent: DetailUiEvent?) = when (uiEvent) {
        is DetailUiEvent.OpenLinkUrl -> openLinkUrl(uiEvent.linkUrl)
        is DetailUiEvent.SetResultWithData -> setResultData(uiEvent.data)
        else -> {}
    }

    private fun initData() {
        val searchData = intent.getParcelableExtra<SearchData>(CLICKED_DATA_FOR_DETAIL)
        if(searchData!=null) detailViewModel.setData(searchData)
    }

    private fun openLinkUrl(linkUrl: String) {
        Intent(Intent.ACTION_VIEW, Uri.parse(linkUrl)).run {
            startActivity(this)
        }
    }

    private fun setResultData(data: SearchData) {
        Intent().apply {
            putExtra(CLICKED_DATA_FOR_DETAIL, data)
        }.let {
            setResult(Activity.RESULT_OK, it)
        }
    }

    override fun onBackPressed() {
        detailViewModel.onBackpressedWithData()
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        eventDisposable?.dispose()
    }

}