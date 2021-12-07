package com.example.newsappdagger2.fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.newsappdagger2.App
import com.example.newsappdagger2.R
import com.example.newsappdagger2.adapters.HomeAdapterRvC
import com.example.newsappdagger2.classses.newapi.Article
import com.example.newsappdagger2.databace.AppDatabaceNews
import com.example.newsappdagger2.databace.entity.UserEntity
import com.example.newsappdagger2.databinding.FragmentSlideBinding
import com.example.newsappdagger2.fragments.bottom.User
import com.example.newsappdagger2.recourse.PagerResource
import com.example.newsappdagger2.viewmodel.MyViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

private const val ARG_PARAM1 = "param1"

class Slide : Fragment(), CoroutineScope {

    private lateinit var fragment: FragmentSlideBinding
    private lateinit var homeAdapterRvC: HomeAdapterRvC
    private var param1: String? = null

    @Inject
    lateinit var appDatabaceNews: AppDatabaceNews

    @Inject
    lateinit var myViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        App.appCompanent.injectslide(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentSlideBinding.inflate(inflater, container, false)

        homeAdapterRvC = HomeAdapterRvC(requireContext(), object : HomeAdapterRvC.onclickkk {
            override fun Click(imageCLass: Article, position: Int) {
                val fragment: Fragment = Info.newInstance(imageCLass)
                val bundle = Bundle()
                bundle.putSerializable("url", imageCLass)
                fragment.arguments = bundle

                findNavController().navigate(R.id.info2)
            }

            override fun clickbookmark(imageCLass: Article, position: Int) {
                if (imageCLass.like) {
                    imageCLass.like = true
                    appDatabaceNews.newsDao().addArticle(
                        UserEntity(
                            author = imageCLass.author,
                            content = imageCLass.content,
                            description = imageCLass.description,
                            publishedAt = imageCLass.publishedAt,
                            title = imageCLass.title,
                            url = imageCLass.url,
                            urlToImage = imageCLass.urlToImage,
                            like = imageCLass.like
                        )
                    )
                } else if (!imageCLass.like) {
                    imageCLass.like = false
                    appDatabaceNews.newsDao().deletBYId(v = imageCLass.author)
                }
            }
        }, appDatabaceNews)

        fragment.rvc.adapter = homeAdapterRvC

        launch {
            myViewModel.getPagerCategory(param1.toString()).collect {
                when (it) {
                    is PagerResource.Loading -> {

                    }
                    is PagerResource.Error -> {
                        fragment.progreess.visibility = View.GONE
                    }
                    is PagerResource.Success -> {
                        fragment.rvc.visibility = View.VISIBLE
                        fragment.progreess.visibility = View.GONE
                        Log.d(TAG, "onCreateView: ${it.list}")
                        homeAdapterRvC.submitList(it.list)
                    }
                }
            }
        }
        return fragment.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            Slide().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }

    override val coroutineContext: CoroutineContext
        get() = Job() + Dispatchers.Main
}