package com.example.examenandroid.Adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examenandroid.databinding.ItemLayoutBinding
import com.example.examenandroid.models.ResponseModel

class AdaptadorLista(
    var dataSet: ArrayList<ResponseModel>
    ) :
    RecyclerView.Adapter<AdaptadorLista.ViewHolderList>() {

    inner class ViewHolderList(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolderList {
        return ViewHolderList(ItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }


    override fun onBindViewHolder(holder: ViewHolderList, position: Int) {
       // holder.binding.response = dataSet[position]
    }

    override fun getItemCount() = dataSet.size

}


