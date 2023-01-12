package com.polodarb.rdogs.ui.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.polodarb.rdogs.R
import com.polodarb.rdogs.data.model.ListOfBreedsModel

class ListOfBreedsRV(private val list: List<ListOfBreedsModel>) :
    RecyclerView.Adapter<ListOfBreedsRV.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var breedPhoto: ImageView = itemView.findViewById(R.id.breedPhoto)
        var breedName: TextView = itemView.findViewById(R.id.breedName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_of_breeds_rv_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.breedName.text = item.name
        holder.breedPhoto.setImageResource(item.photo)
    }

    override fun getItemCount(): Int = list.size

}