package com.polodarb.rdogs.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.polodarb.rdogs.R
import com.polodarb.rdogs.databinding.FragmentPhotosOfDogsBinding
import com.polodarb.rdogs.ui.recyclers.PhotosOfBreedsRV
import com.polodarb.rdogs.ui.viewModels.PhotosOfBreedsViewModel
import com.polodarb.rdogs.ui.viewModels.UiStatePOF
import com.polodarb.rdogs.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.net.URLEncoder

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
                val convertedResult = result.lowercase()

                val words = convertedResult.split("\\s+".toRegex()).map { word ->
                    word.replace("""^[,\.]|[,\.]$""".toRegex(), "")
                }

                if (words.count() > 1)
                    viewModel.getPhotosBySubBreed(words[0], words[1])
                else
                    viewModel.getPhotosByBreed(words[0])

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.state.collect { uiState ->
                    when (uiState) {
                        is UiStatePOF.Success -> {
                            binding.errorLoadPhotosLayout.root.visibility = View.GONE
                            binding.shimmerLayout.visibility = View.GONE
                            val adapter = PhotosOfBreedsRV(uiState.data)
                            binding.rvPhotos.adapter = adapter
                        }

                        is UiStatePOF.Loading -> {
                            binding.shimmerLayout.visibility = View.VISIBLE
                            binding.shimmerLayout.startShimmer()
                        }

                        is UiStatePOF.Error -> {
                            binding.shimmerLayout.visibility = View.GONE
                            binding.rvPhotos.visibility = View.GONE
                            binding.errorLoadPhotosLayout.root.visibility = View.VISIBLE
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