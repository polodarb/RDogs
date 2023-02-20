package com.polodarb.rdogs.ui.fragments

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.HapticFeedbackConstants
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.polodarb.rdogs.R
import com.polodarb.rdogs.databinding.FragmentNetworkErrorBinding
import com.polodarb.rdogs.ui.viewModels.ListOfBreedsViewModel
import com.polodarb.rdogs.utils.Utils

class NetworkErrorFragment : Fragment() {


    private val binding: FragmentNetworkErrorBinding by lazy {
        FragmentNetworkErrorBinding.inflate(
            layoutInflater
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.retryBtn.setOnClickListener {
            binding.retryBtn.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_RELEASE)
            if (Utils.isNetworkAvailable(requireContext())) {
                findNavController().navigate(R.id.action_networkErrorFragment_to_listOfBreedsFragment)
            } else {
                Toast.makeText(requireContext(), "Internet connection required", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        binding.connectionSettingsBtn.setOnClickListener {
            binding.connectionSettingsBtn.performHapticFeedback(HapticFeedbackConstants.KEYBOARD_RELEASE)
            startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
        }

    }
}