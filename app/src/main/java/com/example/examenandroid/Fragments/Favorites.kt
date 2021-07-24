package com.example.examenandroid.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenandroid.Adapters.AdaptadorLista
import com.example.examenandroid.DialogPersonalized
import com.example.examenandroid.R
import com.example.examenandroid.ViewModel.ViewModelClass
import com.example.examenandroid.models.ResponseModel
import kotlinx.android.synthetic.main.init_fragment.*
import org.koin.android.ext.android.inject

class Favorites : Fragment() {

    private val viewModel: ViewModelClass by activityViewModels()
    private val dialog: DialogPersonalized by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.init_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        filtrar_fav.visibility = View.GONE
        borrar_todo.visibility = View.GONE
        refrescar.visibility = View.GONE
        val lista = viewModel.listaPost.value!!.filter{it.isFavourite!!}
        if(lista.isEmpty()){
            dialog.funcion = { Navigation.findNavController(filtrar_fav).navigate(R.id.initFragment) }
            dialog.contenido = "No hay elementos en favoritos, presione OK para volver"
            dialog.showDialog()
        }
        recyclerRefresh(viewModel.listaPost.value!!.filter{it.isFavourite!!} as ArrayList<ResponseModel>)
    }

    fun recyclerRefresh(listPost:ArrayList<ResponseModel>){
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recycler_response.layoutManager = linearLayoutManager
        recycler_response.adapter = AdaptadorLista(viewModel,listPost)
    }
}