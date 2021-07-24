package com.example.examenandroid.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenandroid.Adapters.AdaptadorLista
import com.example.examenandroid.DialogPersonalized
import com.example.examenandroid.Proxi.RetrofitController
import com.example.examenandroid.R
import com.example.examenandroid.ViewModel.ViewModelClass
import com.example.examenandroid.models.ResponseModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.init_fragment.*
import org.koin.android.ext.android.inject

class InitFragment : Fragment() {

    private val viewModel: ViewModelClass by activityViewModels()
    private var realm: Realm? = null
    private val retrofitController:RetrofitController by inject()
    private val dialog:DialogPersonalized by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       // val binding: InitFragmentBinding
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.init_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedElement.value = null
        Realm.init(context)
        dialog.context = context
        realm = Realm.getInstance(RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .build())

        if(realm!!.isEmpty)refreshRequest()
        else{
            viewModel.listaPost.value = realm!!.where<ResponseModel>().findAll().toList() as ArrayList<ResponseModel>
            Log.e("REALM NO VACIO", viewModel.listaPost.value.toString())
            recyclerRefresh(viewModel.listaPost.value!!)
        }
        refrescar.setOnClickListener {
            deleteAll()
            refreshRequest()
        }
        borrar_todo.setOnClickListener {
            deleteAll()
            recyclerRefresh(viewModel.listaPost.value!!)
        }
        filtrar_fav.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.favourites)
            //recyclerRefresh(viewModel.listaPost.value!!.filter{it.isFavourite!!} as ArrayList<ResponseModel>)
        }
    }

    fun recyclerRefresh(listPost:ArrayList<ResponseModel>){
        recycler_response.layoutManager = LinearLayoutManager(requireContext())
        recycler_response.adapter = AdaptadorLista(viewModel,listPost)
    }


    private fun refreshRequest(){
        retrofitController.executeAPI ("posts",{
            if (it.code().toString() == "200") {
                viewModel.listaPost.value = it.body()
                realm?.executeTransaction {
                    it.insert(viewModel.listaPost.value as ArrayList)
                    recyclerRefresh(viewModel.listaPost.value!!)
                }
                Log.e("REALM VACIO", viewModel.listaPost.value.toString())
            } else {
                dialog.contenido = "Respuesta del servidor invalida."
                dialog.showDialog()
            }
        },{
            dialog.contenido = "La información no pudo ser recibida satisfactoriamente, revise su conexion a internet"
            dialog.showDialog()

        })
    }


    private fun deleteAll(){
        realm?.executeTransaction{it.where<ResponseModel>().findAll().deleteAllFromRealm()}
        viewModel.listaPost.value = ArrayList()
    }


}
