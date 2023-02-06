package com.polodarb.rdogs.ui.fragments

import android.content.Intent
import android.net.Uri
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.polodarb.rdogs.R
import com.polodarb.rdogs.data.remote.RetrofitObject
import com.polodarb.rdogs.databinding.ListOfBreedsBinding
import com.polodarb.rdogs.ui.recyclers.ItemClickListener
import com.polodarb.rdogs.ui.recyclers.ListOfBreedsRV
import com.polodarb.rdogs.ui.viewModels.ListOfBreedsViewModel
import com.polodarb.rdogs.ui.viewModels.UiState
import com.polodarb.rdogs.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListOfBreedsFragment : Fragment() {

    private val binding: ListOfBreedsBinding by lazy { ListOfBreedsBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvMain.layoutManager = LinearLayoutManager(requireContext())

        val viewModel: ListOfBreedsViewModel by viewModels()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.CREATED) {
                viewModel.state.collect { uiState ->
                    when (uiState) {
                        is UiState.Success -> {
                            setAdapter(uiState.data)
                            binding.progressHorizontal.isVisible = false
                        }

                        is UiState.Loading -> {
                            binding.progressHorizontal.isVisible = true
                        }

                        is UiState.Error -> Toast.makeText(activity, "error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            }
        }

        setUpGitHubIcon()
    }

    private fun setAdapter(list: List<String>) {
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