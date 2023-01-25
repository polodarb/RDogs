package com.polodarb.rdogs.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.polodarb.rdogs.R
import com.polodarb.rdogs.data.remote.RetrofitObject
import com.polodarb.rdogs.databinding.ListOfBreedsBinding
import com.polodarb.rdogs.ui.recyclers.ItemClickListener
import com.polodarb.rdogs.ui.recyclers.ListOfBreedsRV
import com.polodarb.rdogs.utils.Utils
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class ListOfBreedsFragment : Fragment() {


    private var _binding: ListOfBreedsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ListOfBreedsBinding.inflate(inflater, container, false)

        binding.rvMain.layoutManager = LinearLayoutManager(requireContext())

        val remoteAPI = RetrofitObject.getInstance()

        MainScope().launch {
            val result = remoteAPI.getListAllBreeds().body()?.message!!
            val resultConvert = Utils.listConverter(result)
            val adapter = ListOfBreedsRV(resultConvert, object : ItemClickListener {
                override fun itemOnClick(item: String) {
                    Toast.makeText(requireContext(), "$item", Toast.LENGTH_SHORT).show()
                }
            })
            binding.rvMain.adapter = adapter
            Log.wtf("LIST", result.toString())
        }

        gitHubIcon()

        return binding.root
    }

    private fun gitHubIcon() {
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}