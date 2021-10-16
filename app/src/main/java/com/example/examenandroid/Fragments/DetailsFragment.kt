package com.example.examenandroid.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.examenandroid.DialogPersonalized
import com.example.examenandroid.Proxi.RetrofitController
import com.example.examenandroid.R
import com.example.examenandroid.viewModel.ViewModelClass
import com.example.examenandroid.databinding.FragmentDetailsBinding
import kotlinx.android.synthetic.main.fragment_details.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.URL


class DetailsFragment : Fragment() {

    private val viewModel: ViewModelClass by activityViewModels()
    private val retrofitController: RetrofitController by inject()
    private val dialog: DialogPersonalized by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler_response.layoutManager = LinearLayoutManager(requireContext())
        dialog.context = requireContext()
        refreshRequest()
        Log.e("fesf","termino")
    }


    private fun refreshRequest(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
               // val url = URL("https://openweathermap.org/data/2.5/weather?lat=$lat&lon=$lon&appid=b6907d289e10d714a6e88b30761fae22")
                //val a = BufferedReader(InputStreamReader(url.openStream()))
                Log.e("efsfe","corrutina")
            } catch (e: Exception) {
                Log.e("Conexion Hilo_GPS", "Fallo")
            }

        }
    }




}