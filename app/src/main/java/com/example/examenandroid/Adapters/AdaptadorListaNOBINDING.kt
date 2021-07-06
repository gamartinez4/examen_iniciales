package com.example.examenandroid.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.examenandroid.R
import com.example.examenandroid.databinding.ItemLayoutBinding
import com.example.examenandroid.models.ResponseModel

class AdaptadorListaNOBINDING(
    var dataSet: ArrayList<ResponseModel>
) : RecyclerView.Adapter<AdaptadorListaNOBINDING.ViewHolderList>() {

    inner class ViewHolderList(view: View) : RecyclerView.ViewHolder(view) {
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolderList {
        return ViewHolderList(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_layout, viewGroup, false))}


    override fun onBindViewHolder(holder: ViewHolderList, position: Int) {
    }

    override fun getItemCount() = dataSet.size

}