package com.polodarb.rdogs.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.polodarb.rdogs.databinding.FragmentPhotosOfDogsBinding
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
        // TODO:  
    }
}