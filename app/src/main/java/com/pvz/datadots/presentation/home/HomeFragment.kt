package com.pvz.datadots.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
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
        viewModel.pointCount.observe(viewLifecycleOwner) { count ->
            binding.etPointCount.setText(count)
        }
        binding.btnGo.setOnClickListener {
            val count = binding.etPointCount.text.toString().toIntOrNull() ?: return@setOnClickListener
            val action = HomeFragmentDirections.actionHomeFragmentToResultsFragment(count)
            findNavController().navigate(action)
        }
    }
}
