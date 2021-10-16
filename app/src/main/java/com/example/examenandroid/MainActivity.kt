package com.example.examenandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var navController : NavController? = null
    private var fragmentId: Int? = null
    private var fragmentView:View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navController =
            (supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment).findNavController()
        navController!!.addOnDestinationChangedListener {_, destination, _ ->
            fragmentId = destination.id
        }
        fragmentView=fragment
    }

    override fun onBackPressed(){
       // when(fragmentId){
         //   R.id.detailsFragment,R.id.favourites->
           //     Navigation.findNavController(fragment).navigate(R.id.initFragment)
        //}
    }


}