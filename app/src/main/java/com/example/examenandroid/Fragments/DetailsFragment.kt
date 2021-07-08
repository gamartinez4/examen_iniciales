package com.example.examenandroid.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import com.example.examenandroid.R
import com.example.examenandroid.ViewModel.ViewModelClass
import com.example.examenandroid.databinding.FragmentDetailsBinding
import com.example.examenandroid.models.ResponseModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.fragment_details.*


class DetailsFragment : Fragment() {

    private val viewModel: ViewModelClass by activityViewModels()
    val post : ResponseModel? by lazy { viewModel.selectedElement.value }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding: FragmentDetailsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_details, container, false)
        binding.response = post!!
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val realm = Realm.getInstance(RealmConfiguration.Builder()
            .allowWritesOnUiThread(true)
            .build())
        realm.executeTransaction{r->
            post?.let{
                it.viewed = true
                r.insertOrUpdate(it)
            }
        }

        errase_post.setOnClickListener {
            realm?.executeTransaction{
                (it.where<ResponseModel>().equalTo("id",post!!.id)).findFirst()!!
                    .deleteFromRealm()
            }
            Navigation.findNavController(it).navigate(R.id.initFragment)
        }

        fav.setOnClickListener {
            realm?.executeTransaction{ r ->
                post!!.isFavourite = true
                r.insertOrUpdate(post!!)
            }
            Navigation.findNavController(it).navigate(R.id.initFragment)
        }
    }


}