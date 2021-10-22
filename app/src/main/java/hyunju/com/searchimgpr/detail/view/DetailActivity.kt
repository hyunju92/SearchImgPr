package hyunju.com.searchimgpr.detail.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.ActivityDetailBinding
import hyunju.com.searchimgpr.detail.vm.DetailViewModel
import hyunju.com.searchimgpr.keep.view.KeepFragment.Companion.IMG_STR
import hyunju.com.searchimgpr.keep.view.KeepFragment.Companion.IS_MARKED

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private val detailViewModel: DetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView<ActivityDetailBinding>(this, R.layout.activity_detail).apply {
            vm = detailViewModel
        }
        initView()
        initData()
    }


    private fun initView() {


    }

    private fun initData() {
        val imgStr = intent.getStringExtra(IMG_STR)
        val isMarked = intent.getBooleanExtra(IS_MARKED, false)
        if(imgStr != null) detailViewModel.setImgData(imgStr, isMarked)
    }

    override fun onBackPressed() {
        Intent().apply {
            putExtra(IS_MARKED, detailViewModel.isMarked.get())
            putExtra(IMG_STR, detailViewModel.imgStr.get())
        }.let {
            setResult(Activity.RESULT_OK, it)
        }

        super.onBackPressed()
    }


}