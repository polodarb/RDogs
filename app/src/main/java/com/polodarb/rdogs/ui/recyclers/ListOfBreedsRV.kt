package com.polodarb.rdogs.ui.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polodarb.rdogs.R
import com.polodarb.rdogs.data.model.ListOfBreedsModel
import com.polodarb.rdogs.databinding.ListOfBreedsRvItemBinding
import com.polodarb.rdogs.databinding.PhotosOfDogsBinding

class ListOfBreedsRV() : RecyclerView.Adapter<ListOfBreedsRV.ViewHolder>() {

    private val list = mutableListOf<ListOfBreedsModel>()

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun bindData(item: ListOfBreedsModel) {
            var breedName = view.findViewById<TextView>(R.id.breedName)
//            var breedPhoto = view.findViewById<ImageView>(R.id.breedPhoto)

            breedName.text = item.name
//            breedPhoto = item.photo
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_of_breeds_rv_item, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(list[position])
    }
}