package hyunju.com.searchimgpr.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.ActivityMainBinding
import hyunju.com.searchimgpr.keep.KeepFragment
import hyunju.com.searchimgpr.search.SearchFragment

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        initView()
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