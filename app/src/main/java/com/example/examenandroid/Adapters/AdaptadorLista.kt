package com.example.examenandroid.Adapters


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.examenandroid.Fragments.InitFragment
import com.example.examenandroid.R
import com.example.examenandroid.ViewModel.ViewModelClass
import com.example.examenandroid.databinding.ItemLayoutBinding
import com.example.examenandroid.models.ResponseModel

class AdaptadorLista<T:Fragment>(
    private var viewModel: ViewModelClass,
    private val listpost:ArrayList<ResponseModel>,
    private val fragmento:T
    ) : RecyclerView.Adapter<AdaptadorLista<T>.ViewHolderList<T>>() {

    inner class ViewHolderList<T>(val binding: ItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                Navigation.findNavController(it).navigate(R.id.detailsFragment)
                viewModel.selectedElement.value = binding.response
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolderList<T>{
        return ViewHolderList(ItemLayoutBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false))
    }

    override fun onBindViewHolder(holder: ViewHolderList<T>, position: Int) {
        holder.binding.response = listpost[position]
        Glide.with(fragmento).load(listpost[position].url).into(holder.binding.imageNasa)
    }

    override fun getItemCount() = listpost.size
}


