package com.polodarb.rdogs.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.polodarb.rdogs.R
import com.polodarb.rdogs.data.model.ListOfBreedsModel
import com.polodarb.rdogs.databinding.ListOfBreedsBinding
import com.polodarb.rdogs.ui.recyclers.ListOfBreedsRV

class ListOfBreedsFragment : Fragment() {


    private var _binding: ListOfBreedsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ListOfBreedsBinding.inflate(inflater, container, false)

        val layoutManager= GridLayoutManager(activity, 2)

        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                return if (position % 7 == 0) 2 else 1
            }
        }
        binding.rvMain.layoutManager = layoutManager

        val data = ArrayList<ListOfBreedsModel>()

        for (i in 0..20) {
            data.add(ListOfBreedsModel("Item $i", R.drawable.img_1))
        }

        val adapter = ListOfBreedsRV(data)

        binding.rvMain.adapter = adapter

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