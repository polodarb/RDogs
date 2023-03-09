package com.polodarb.rdogs.ui.fragments

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.polodarb.rdogs.R
import com.polodarb.rdogs.databinding.FragmentPhotosOfDogsBinding
import com.polodarb.rdogs.ui.recyclers.PhotosOfBreedsRV
import com.polodarb.rdogs.ui.viewModels.ListOfBreedsViewModel
import com.polodarb.rdogs.ui.viewModels.PhotosOfBreedsViewModel
import com.polodarb.rdogs.ui.viewModels.UiState
import com.polodarb.rdogs.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Locale

@AndroidEntryPoint
class PhotosOfDogsFragment : Fragment() {

    private val binding: FragmentPhotosOfDogsBinding by lazy {
        FragmentPhotosOfDogsBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        backButton()

        val viewModel: PhotosOfBreedsViewModel by viewModels()

        binding.rvPhotos.layoutManager = GridLayoutManager(requireContext(), 2)

        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getString("bundleKey")
            binding.topbarPhotos.title = result

            if (result != null) {
                val convertedResult = result.lowercase().replace(' ', '/')
                Toast.makeText(requireContext(), "$convertedResult", Toast.LENGTH_SHORT).show()
                viewModel.getPhotos(convertedResult)
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { uiState ->
                    when (uiState) {
                        is UiState.Success -> {
                            binding.shimmerLayout.visibility = View.GONE
                            val adapter = PhotosOfBreedsRV(uiState.data as List<String>)
                            binding.rvPhotos.adapter = adapter
                        }

                        is UiState.Loading -> {
                            binding.shimmerLayout.visibility = View.VISIBLE
                            binding.shimmerLayout.startShimmer()
                        }

                        is UiState.Error -> {
                            findNavController().navigate(R.id.action_listOfBreedsFragment_to_networkErrorFragment)
                            binding.shimmerLayout.visibility = View.GONE
                        }
                    }
                }
            }
        }
    }

    private fun backButton() {
        binding.topbarPhotos.setNavigationOnClickListener {
            Utils.setHapticEffect(binding.topbarPhotos)
            findNavController().popBackStack()
        }
    }
}