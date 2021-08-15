package com.example.examenandroid.Fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
import org.json.JSONObject
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
            viewModel.listaPost = realm!!.where<ResponseModel>().findAll().toList() as ArrayList<ResponseModel>
            Log.e("REALM NO VACIO", viewModel.listaPost.toString())
            recyclerRefresh(viewModel.listaPost)
        }
        refrescar.setOnClickListener {
            deleteAll()
            refreshRequest()
        }
        borrar_todo.setOnClickListener {
            deleteAll()
            recyclerRefresh(viewModel.listaPost)
        }
        filtrar_fav.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.favourites)
        }

        search.addTextChangedListener(object: TextWatcher {override fun afterTextChanged(s: Editable?) {

        }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val textoBuscar = search.text.toString()
                var responseModelFilt  = viewModel.listaPost
                if(textoBuscar!=null && textoBuscar != "") {
                    val responseModelFiltAux = ArrayList<ResponseModel>()
                    for (respuesta in responseModelFilt) {
                        if (respuesta.title!!.contains(textoBuscar)) responseModelFiltAux.add(respuesta)
                    }
                    responseModelFilt = responseModelFiltAux
                }
                recyclerRefresh(responseModelFilt)
            }

        })

    }

    fun recyclerRefresh(listPost:ArrayList<ResponseModel>){
        recycler_response.layoutManager = LinearLayoutManager(requireContext())
        recycler_response.adapter = AdaptadorLista(viewModel,listPost,this)
    }


    private fun refreshRequest(){
        retrofitController.executeAPI ("search","apollo 11",{
            try {
                if (it.code().toString() == "200") {

                    val jsonArrayData = JSONObject(it.body().toString()).getJSONObject("collection")
                        .getJSONArray("items")

                    for (i in 0 until jsonArrayData.length()-1) {
                        try {
                            val responseElement = ResponseModel()
                            val jsonObject = jsonArrayData.getJSONObject(i)


                            responseElement.id = i
                            responseElement.url = jsonObject.getJSONArray("links").getJSONObject(0)
                                .getString("href")
                            responseElement.title = jsonObject.getJSONArray("data").getJSONObject(0)
                                .getString("title")
                            viewModel.listaPost.add(responseElement)
                        }catch (e:Exception){}
                        //Log.e("prueba2","${responseElement.title.toString()} ${responseElement.url.toString()}")
                    }
                    Log.e("prueba",viewModel.listaPost.toString())

                    realm?.executeTransaction {
                        it.insert(viewModel.listaPost)
                        recyclerRefresh(viewModel.listaPost)
                    }

                    Log.e("REALM VACIO", viewModel.listaPost.toString())
                } else {
                    dialog.contenido = "Respuesta del servidor invalida."
                    Log.e("respuesta", it.code().toString())
                    dialog.showDialog()
                }
            }catch (e:Exception){
                Log.e("Exception",e.toString())
            }
        },{
            dialog.contenido = "La información no pudo ser recibida satisfactoriamente, revise su conexion a internet"
            dialog.showDialog()
        })
    }


    private fun deleteAll(){
        realm?.executeTransaction{it.where<ResponseModel>().findAll().deleteAllFromRealm()}
        viewModel.listaPost = ArrayList()
    }



}
