package hyunju.com.searchimgpr.keep.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.FragmentKeepBinding
import hyunju.com.searchimgpr.main.vm.SharedViewModel

class KeepFragment : Fragment() {

    private lateinit var binding: FragmentKeepBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val keepViewModel: SharedViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<FragmentKeepBinding>(inflater, R.layout.fragment_keep, container, false).apply {

        }
        return binding.root
    }
}