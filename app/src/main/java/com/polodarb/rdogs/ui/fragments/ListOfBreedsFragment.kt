package com.polodarb.rdogs.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.polodarb.rdogs.R
import com.polodarb.rdogs.data.local.Breeds
import com.polodarb.rdogs.databinding.FragmentListOfBreedsBinding
import com.polodarb.rdogs.ui.recyclers.ItemClickListener
import com.polodarb.rdogs.ui.recyclers.ListOfBreedsRV
import com.polodarb.rdogs.ui.viewModels.ListOfBreedsViewModel
import com.polodarb.rdogs.ui.viewModels.UiState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListOfBreedsFragment : Fragment() {

    private val binding: FragmentListOfBreedsBinding by lazy { FragmentListOfBreedsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMain.layoutManager = LinearLayoutManager(requireContext())

        val viewModel: ListOfBreedsViewModel by viewModels()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModelStates(viewModel)
            }
        }

        setUpGitHubIcon()
    }

    private suspend fun viewModelStates(viewModel: ListOfBreedsViewModel) {
        viewModel.state.collect { uiState ->
            when (uiState) {
                is UiState.Success -> {
                    binding.shimmerLayout.visibility = View.GONE
                    setAdapter(uiState.data)
                }

                is UiState.Loading -> {
                    loadingStateVisibility()
                    binding.shimmerLayout.visibility = View.VISIBLE
                    binding.shimmerLayout.startShimmer()
                }

                is UiState.Error -> {
                    findNavController().navigate(R.id.action_listOfBreedsFragment_to_networkErrorFragment)
                    binding.shimmerLayout.visibility = View.GONE
                    errorStateVisibility()
                }
            }
        }
    }

    private fun errorStateVisibility() {
        binding.rvMain.isVisible = false
        binding.topAppBar.isVisible = false
        binding.appbarMain.isVisible = false
        binding.collapseToolbar.isVisible = false
    }

    private fun loadingStateVisibility() {
        binding.rvMain.isVisible = true
        binding.topAppBar.isVisible = true
        binding.appbarMain.isVisible = true
        binding.collapseToolbar.isVisible = true
    }

    private fun setAdapter(list: List<Breeds>) {
        val adapter = ListOfBreedsRV(list, object : ItemClickListener {
            override fun itemOnClick(item: String) {
                Toast.makeText(requireContext(), item, Toast.LENGTH_SHORT).show()
            }
        })
        binding.rvMain.adapter = adapter
    }

    private fun setUpGitHubIcon() {
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.settings -> {
                    val i = Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/polodarb"))
                    startActivity(i)
                    true
                }

                else -> false
            }
        }
    }
}