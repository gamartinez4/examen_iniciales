package com.example.examenandroid.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.examenandroid.DialogPersonalized
import com.example.examenandroid.Proxi.RetrofitController
import com.example.examenandroid.R
import com.example.examenandroid.viewModel.ViewModelClass
import com.example.examenandroid.databinding.InitFragmentBinding
import io.realm.Realm
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
    ): View {
        val binding: InitFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.init_fragment, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        search_layout.setStartIconOnClickListener{
            Navigation.findNavController(it)
                .navigate(R.id.detailsFragment,null,null)

        }

    }

}
