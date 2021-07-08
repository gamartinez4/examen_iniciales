package com.example.examenandroid.Adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.examenandroid.R
import com.example.examenandroid.ViewModel.ViewModelClass
import com.example.examenandroid.databinding.ItemLayoutBinding
import com.example.examenandroid.models.ResponseModel

class AdaptadorLista(
    private var viewModel: ViewModelClass,
    private val listpost:ArrayList<ResponseModel>
    ) :

    RecyclerView.Adapter<AdaptadorLista.ViewHolderList>() {

    inner class ViewHolderList(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {

                Navigation.findNavController(it).navigate(R.id.detailsFragment)
                viewModel.selectedElement.value = binding.response
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolderList {
        return ViewHolderList(ItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }


    override fun onBindViewHolder(holder: ViewHolderList, position: Int) {
        holder.binding.response = listpost[position]
    }

    override fun getItemCount() = listpost.size

}


