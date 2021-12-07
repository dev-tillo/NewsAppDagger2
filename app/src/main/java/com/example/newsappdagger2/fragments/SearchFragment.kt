package com.example.newsappdagger2.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.speech.RecognizerIntent
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.example.newsappdagger2.App
import com.example.newsappdagger2.R
import com.example.newsappdagger2.adapters.HomeAdapterRvC
import com.example.newsappdagger2.adapters.HomeRvAdapter
import com.example.newsappdagger2.classses.newapi.Article
import com.example.newsappdagger2.databinding.FragmentSearchBinding
import com.example.newsappdagger2.fragments.bottom.Home
import com.example.newsappdagger2.recourse.PagerResource
import com.example.newsappdagger2.viewmodel.MyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

private const val ARG_PARAM1 = "Bitcoin"
private const val ARG_PARAM2 = "param2"

class SearchFragment : Fragment(), CoroutineScope {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var fragment: FragmentSearchBinding
    private lateinit var homeAdapterRvC: HomeRvAdapter
    private var mediaPlayer: MediaPlayer? = null
    private var url = ""

    @Inject
    lateinit var myViewModel: MyViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentSearchBinding.inflate(inflater, container, false)
        fragment.apply {
            App.appCompanent.injectsearch(this@SearchFragment)

            left.setOnClickListener {
                findNavController().popBackStack()
            }

            edittext.queryHint = "Search..."
            edittext.setOnQueryTextListener(object :
                SearchView.OnQueryTextListener,
                androidx.appcompat.widget.SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(p0: String?): Boolean {
                    if (p0.toString().contains(" ")) {
                        Toast.makeText(
                            requireContext(),
                            "Only word is required!",
                            Toast.LENGTH_SHORT
                        ).show()
                        return false
                    } else {
                        launch {
                            myViewModel.getPagerCategory(p0.toString()).collect {
                                when (it) {
                                    is PagerResource.Loading -> {
                                        fragment.rvc.visibility = View.GONE
                                    }
                                    is PagerResource.Error -> {
                                        fragment.progreess.visibility = View.GONE
                                        fragment.rvc.visibility = View.GONE
                                    }
                                    is PagerResource.Success -> {
                                        fragment.rvc.visibility = View.VISIBLE
                                        fragment.progreess.visibility = View.GONE
                                        Log.d(ContentValues.TAG, "onCreateView: ${it.list}")
                                        homeAdapterRvC.submitList(it.list)
                                    }
                                }
                            }
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })

            fragment.microfon.setOnClickListener {
                val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
                intent.putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                startActivityForResult(intent, 200)
            }

            homeAdapterRvC = HomeRvAdapter(requireContext(), object : HomeRvAdapter.onClick {
                override fun Click(imageCLass: Article, position: Int) {
                    Toast.makeText(requireContext(), "Bu amaliyot mavjud emas", Toast.LENGTH_SHORT)
                        .show()
                }
            })
            fragment.rvc.adapter = homeAdapterRvC

        }
        return fragment.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 200 && resultCode == RESULT_OK) {
            val arrayList = ArrayList(data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS))
            val voice = arrayList.get(0)
            fragment.edittext.setQuery(voice, true)
        } else {
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main
}