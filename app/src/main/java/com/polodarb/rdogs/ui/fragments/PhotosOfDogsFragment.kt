package com.polodarb.rdogs.ui.fragments

import android.os.Build
import android.os.Bundle
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.polodarb.rdogs.databinding.FragmentPhotosOfDogsBinding
import com.polodarb.rdogs.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PhotosOfDogsFragment : Fragment() {

    private val binding: FragmentPhotosOfDogsBinding by lazy { FragmentPhotosOfDogsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        backButton()

        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getString("bundleKey")
            binding.topbarPhotos.title = result
        }
    }

    private fun backButton() {
        binding.topbarPhotos.setNavigationOnClickListener {
            Utils.setHapticEffect(binding.topbarPhotos)
            findNavController().popBackStack()
        }
    }
}