package com.example.examenandroid.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenandroid.Adapters.AdaptadorLista
import com.example.examenandroid.Proxi.ApiRetrofit
import com.example.examenandroid.R
import com.example.examenandroid.ViewModel.ViewModelClass
import com.example.examenandroid.models.ResponseModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmResults
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.init_fragment.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class InitFragment : Fragment() {

    private val viewModel: ViewModelClass by activityViewModels()
    private var realm: Realm? = null

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
        val linearLayoutManager = LinearLayoutManager(requireContext())
        recycler_response.layoutManager = linearLayoutManager
        recycler_response.adapter = AdaptadorLista(viewModel,listPost)
    }


    private fun refreshRequest(){
        val retrofit: Retrofit? = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()
        retrofit?.let {
            it.create(ApiRetrofit::class.java).getAllPost()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    if (it.code().toString() == "200") {
                        viewModel.listaPost.value = it.body()
                        Log.e("REALM VACIO", viewModel.listaPost.value.toString())
                        realm?.executeTransaction {
                            it.insert(viewModel.listaPost.value as ArrayList)
                        }
                        //Log.e("prueba", realm!!.where<ResponseModel>().findAll().toString())
                        recyclerRefresh(viewModel.listaPost.value!!)
                    } else Log.e("RESPUESTA RETROFIT", "NO VALIDA")
                }
        }
    }


    private fun deleteAll(){
        realm?.executeTransaction{it.where<ResponseModel>().findAll().deleteAllFromRealm()}
        viewModel.listaPost.value = ArrayList()
    }


}
