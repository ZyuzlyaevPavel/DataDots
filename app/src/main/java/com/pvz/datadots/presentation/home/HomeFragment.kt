package com.pvz.datadots.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.pvz.datadots.R
import com.pvz.datadots.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private val binding by lazy {
        FragmentHomeBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.navigationEvent.asLiveData().observe(viewLifecycleOwner) {
            try {
                val action = HomeFragmentDirections.actionHomeFragmentToResultsFragment()
                Log.d("main", "navigate($action)")
                findNavController().navigate(action)

            } catch (e: Exception) {
                Log.e("main", "navigation error", e)
            }
        }

        viewModel.errorEvent.asLiveData().observe(viewLifecycleOwner) { error ->
            val message = when(error){
                is PointFetchError.RemoteError -> getString(
                    R.string.home_toast_remote_error,
                    error.responseCode.toString()
                )
                PointFetchError.UnknownRemoteError -> getString(R.string.home_toast_unknown_remote_error)
                PointFetchError.ValueError -> getString(R.string.home_toast_value_error)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
        }
        viewModel.pointCount.observe(viewLifecycleOwner) { text ->
            if (binding.etPointCount.text.toString() != text) {
                binding.etPointCount.setText(text)
            }
        }

        binding.btnGo.setOnClickListener {
            val count =
                binding.etPointCount.text.toString().toIntOrNull()
            viewModel.fetchPoints(count)
        }
        binding.etPointCount.addTextChangedListener{
            viewModel.setPointCount(it.toString())
        }
    }
}
