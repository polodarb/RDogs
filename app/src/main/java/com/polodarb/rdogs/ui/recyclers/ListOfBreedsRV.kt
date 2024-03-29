package com.polodarb.rdogs.ui.recyclers

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.polodarb.rdogs.R
import com.polodarb.rdogs.data.local.Breed
import com.polodarb.rdogs.databinding.ListOfBreedsRvItemBinding
import com.polodarb.rdogs.utils.Utils

interface ItemClickListener {
    fun itemOnClick(item: String)
}

class ListOfBreedsRV(
    private val list: List<Breed>,
    private val mItemClickListener: ItemClickListener
) :
    RecyclerView.Adapter<ListOfBreedsRV.ViewHolder>(), View.OnClickListener {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var breedName: TextView = itemView.findViewById(R.id.breedName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = ListOfBreedsRvItemBinding.inflate(inflater, parent, false)

        view.root.setOnClickListener(this)

        return ViewHolder(view.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        with(holder) {
            breedName.text = item.breed
            itemView.tag = item.breed
        }
    }

    override fun getItemCount(): Int = list.size

    @RequiresApi(Build.VERSION_CODES.O_MR1)
    override fun onClick(v: View) {
        val itemBreed = v.tag as String
        Utils.setHapticEffect(v)
        if (v.id == R.id.card) mItemClickListener.itemOnClick(itemBreed)
    }

}