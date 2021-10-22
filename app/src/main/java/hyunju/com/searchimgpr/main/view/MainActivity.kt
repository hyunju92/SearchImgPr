package hyunju.com.searchimgpr.main.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.ActivityMainBinding
import hyunju.com.searchimgpr.keep.view.KeepFragment
import hyunju.com.searchimgpr.keep.view.KeepFragment.Companion.START_DETAIL_FROM_KEEP
import hyunju.com.searchimgpr.search.view.SearchFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("testMainActivityResult", "init requestCode $requestCode")
        Log.d("testMainActivityResult", "init resultCode $resultCode")

        when(requestCode) {
            START_DETAIL_FROM_KEEP -> {
                Log.d("testMainActivityResult", "START_DETAIL_FROM_KEEP")
            }
        }

    }

    private fun initView() {
        val tabs = listOf(SearchFragment(), KeepFragment())
        val tabNames = listOf("검색", "보관")

        binding.mainViewPager.apply {
            adapter = MainViewPagerAdapter(tabs, this@MainActivity)
        }

        TabLayoutMediator(binding.mainTab, binding.mainViewPager, true, false) { tab, position ->
            tab.text = tabNames[position]
        }.attach()
    }

    inner class MainViewPagerAdapter(private val tabs: List<Fragment>, fragment: FragmentActivity) :
        FragmentStateAdapter(fragment) {
        override fun createFragment(position: Int): Fragment = tabs[position]
        override fun getItemCount(): Int = tabs.size
    }
}