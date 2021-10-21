package hyunju.com.searchimgpr.search.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import hyunju.com.searchimgpr.R
import hyunju.com.searchimgpr.databinding.FragmentSearchBinding
import hyunju.com.searchimgpr.main.vm.SharedViewModel

class SearchFragment : Fragment() {

    private lateinit var binding: FragmentSearchBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val searchViewModel: SharedViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate<FragmentSearchBinding>(inflater, R.layout.fragment_search, container, false).apply {

        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}