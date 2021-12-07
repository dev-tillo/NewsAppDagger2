package com.example.newsappdagger2.fragments.bottom

import android.content.ContentValues.TAG
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.newsappdagger2.App
import com.example.newsappdagger2.R
import com.example.newsappdagger2.adapterpager.VpforHome
import com.example.newsappdagger2.adapters.HomeRvAdapter
import com.example.newsappdagger2.classses.newapi.Article
import com.example.newsappdagger2.databinding.BackLayoutBinding
import com.example.newsappdagger2.databinding.FragmentHomeBinding
import com.example.newsappdagger2.fragments.Info
import com.example.newsappdagger2.fragments.SearchFragment
import com.example.newsappdagger2.recourse.UserResource
import com.example.newsappdagger2.viewmodel.MyViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext
import kotlin.math.abs

class Home : Fragment(), CoroutineScope {

    private lateinit var fragment: FragmentHomeBinding
    private lateinit var vpforHome: VpforHome
    private lateinit var homeRvAdapter: HomeRvAdapter
    val compotitionId = 0
    var isCreat = false

    @Inject
    lateinit var myViewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentHomeBinding.inflate(inflater, container, false)

        homeRvAdapter = HomeRvAdapter(requireContext(), object : HomeRvAdapter.onClick {
            override fun Click(imageCLass: Article, position: Int) {
                val fragment: Fragment = Info.newInstance(imageCLass)
                val bundle = Bundle()
                bundle.putSerializable("url", imageCLass)
                fragment.arguments = bundle
                findNavController().navigate(R.id.info2)
            }
        })

        tabSet()

        fragment.card.setOnClickListener {
            findNavController().navigate(R.id.searchFragment)
        }

        return fragment.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        App.appCompanent.inject(this)

        fragment.rvc.adapter = homeRvAdapter
        if (!isCreat) {
            loadUi(compotitionId)
            isCreat = true
        }
    }

    private fun loadUi(compotitionId: Int) {
        launch {
            myViewModel.getCategory().collect {
                when (it) {
                    is UserResource.Loading -> {
                        fragment.progress.visibility = View.VISIBLE
                        fragment.rvc.visibility = View.GONE
                    }
                    is UserResource.Error -> {
                        fragment.progress.visibility = View.GONE
                        fragment.rvc.visibility = View.GONE
                    }
                    is UserResource.Success -> {
                        fragment.rvc.visibility = View.VISIBLE
                        fragment.progress.visibility = View.GONE
                        homeRvAdapter.submitList(it.list)
                        Log.d(TAG, "loadUi: ${it.list}")
                    }
                }
            }
        }
    }

    private fun tabSet() {
        val arr = arrayListOf(
            "Sports",
            "Politics",
            "Life",
            "Gaming",
            "Animals",
            "Nature",
            "Food",
            "Art",
            "History",
            "Fashion",
            "Covid 19",
            "Middle East"
        )

        vpforHome = VpforHome(this, arr)

        fragment.viewpager.adapter = vpforHome
        fragment.viewpager.isUserInputEnabled = false

        TabLayoutMediator(fragment.tablayout, fragment.viewpager) { tab, position ->
            var bind = BackLayoutBinding.inflate(layoutInflater)
            tab.customView = bind.root
            bind.texts.text = arr[position]
            if (position == 0) {
                bind.backs.visibility = View.VISIBLE
                bind.texts.setTextColor(Color.WHITE)
            } else {
                bind.backs.visibility = View.INVISIBLE
                bind.texts.setTextColor(Color.BLACK)
            }
        }.attach()

        fragment.tablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                var bind = BackLayoutBinding.bind(tab?.customView!!)
                bind.backs.visibility = View.VISIBLE
                bind.texts.setTextColor(Color.WHITE)
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                var bind = BackLayoutBinding.bind(tab?.customView!!)
                bind.backs.visibility = View.INVISIBLE
                bind.texts.setTextColor(Color.BLACK)
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {

            }
        })

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(10))
        compositePageTransformer.addTransformer { page, position ->
            val r: Float = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        fragment.viewpager.clipToPadding = false
        fragment.viewpager.clipChildren = false
        fragment.viewpager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        fragment.viewpager.setPageTransformer(compositePageTransformer)

    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + Job()
}