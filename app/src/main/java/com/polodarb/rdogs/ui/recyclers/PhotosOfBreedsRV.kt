package com.polodarb.rdogs.ui.recyclers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.polodarb.rdogs.R
import com.polodarb.rdogs.databinding.PhotosOfDogsRvItemBinding

class PhotosOfBreedsRV(
    private val list: List<String>
) :
    RecyclerView.Adapter<PhotosOfBreedsRV.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView = itemView.findViewById(R.id.breedPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotosOfBreedsRV.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = PhotosOfDogsRvItemBinding.inflate(inflater, parent, false)

        return PhotosOfBreedsRV.ViewHolder(view.root)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PhotosOfBreedsRV.ViewHolder, position: Int) {
        val item = list[position]

        holder.image.load(item) {
            crossfade(true)
        }
    }

}