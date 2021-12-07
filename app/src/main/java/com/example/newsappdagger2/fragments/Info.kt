package com.example.newsappdagger2.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.newsappdagger2.App
import com.example.newsappdagger2.R
import com.example.newsappdagger2.classses.newapi.Article
import com.example.newsappdagger2.databace.AppDatabaceNews
import com.example.newsappdagger2.databace.entity.UserEntity
import com.example.newsappdagger2.databinding.FragmentInfoBinding
import com.example.newsappdagger2.fragments.bottom.Home
import com.example.newsappdagger2.fragments.bottom.ReadDb
import com.example.newsappdagger2.fragments.bottom.User
import com.squareup.picasso.Picasso
import javax.inject.Inject


private const val ARG_PARAM1 = "url"

class Info : Fragment() {

    private lateinit var fragment: FragmentInfoBinding
    private var param1: Article? = null

    @Inject
    lateinit var appDatabaceNews: AppDatabaceNews

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as Article
        }
        (activity as BasicActivity?)?.hideBlurView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentInfoBinding.inflate(inflater, container, false)
        App.appCompanent.inject(this)

        fragment.imageOnBg.setOnClickListener {
            findNavController().popBackStack()
        }

        fragment.backImg.setOnClickListener {
            findNavController().popBackStack()
        }

        Glide.with(requireContext()).load(param1?.urlToImage)
            .placeholder(R.drawable.ic_launcher_foreground).into(fragment.image)

        Picasso.get().load(param1?.urlToImage).placeholder(R.drawable.ic_launcher_foreground)
            .into(fragment.imageOnBg)

        fragment.apply {
            sourceNameTv.text = param1?.content
            titleTv.text = param1?.title
            newsDataTv.text = param1?.description

            shareImg.setOnClickListener {
                val sendIntent: Intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, param1?.urlToImage)
                    type = "text/plain"
                }
                val shareIntent = Intent.createChooser(sendIntent, null)
                startActivity(shareIntent)
            }

            val list = appDatabaceNews.newsDao().getList()

            for (userEntity in list) {
                if (userEntity.like == false) {
                    userEntity.like = false
                    saveImg.setImageResource(R.drawable.bookmark)
                } else {
                    userEntity.like = true
                    saveImg.setImageResource(R.drawable.bookmarkkkk)
                }
            }
//            if (param1?.like == false) {
//                saveImg.setImageResource(R.drawable.bookmark)
//            } else {
//                saveImg.setImageResource(R.drawable.bookmarkkkk)
//            }

            saveImg.setOnClickListener {
                if (param1?.like == false) {
                    param1?.like = true
                    saveImg.setImageResource(R.drawable.bookmarkkkk)
                    appDatabaceNews.newsDao()
                        .addArticle(
                            UserEntity(
                                author = param1?.author.toString(),
                                content = param1?.content.toString(),
                                description = param1?.description.toString(),
                                publishedAt = param1?.publishedAt.toString(),
                                title = param1?.title.toString(),
                                url = param1?.url.toString(),
                                urlToImage = param1?.urlToImage.toString(),
                                like = param1?.like == true,
                            )
                        )
                } else if (param1?.like == true) {
                    param1?.like = false
                    saveImg.setImageResource(R.drawable.bookmark)
                    appDatabaceNews.newsDao().deletBYId(v = param1?.author.toString())
                }
            }
        }
        return fragment.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as BasicActivity).showBlurView()
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Article) =
            Info().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}