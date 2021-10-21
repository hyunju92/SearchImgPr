package hyunju.com.searchimgpr.keep

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.FragmentKeepBinding

class KeepFragment : Fragment() {

    private lateinit var binding: FragmentKeepBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<FragmentKeepBinding>(inflater, R.layout.fragment_keep, container, false).apply {

        }
        return binding.root
    }
}