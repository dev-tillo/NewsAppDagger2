package com.example.newsappdagger2.pager

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.newsappdagger2.R
import com.example.newsappdagger2.adapterpager.Pager
import com.example.newsappdagger2.databinding.FragmentFramlayoutBinding
import com.example.newsappdagger2.fragments.BasicActivity
import com.example.newsappdagger2.fragments.Category
import com.example.newsappdagger2.models.User

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class Framlayout : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    lateinit var fragment: FragmentFramlayoutBinding
    private lateinit var pager: Pager
    private lateinit var list: ArrayList<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentFramlayoutBinding.inflate(inflater, container, false)

        loadData()
        pager = Pager(this, list)
        fragment.viewpager.adapter = pager
        fragment.dotsIndicator.setViewPager2(fragment.viewpager)
        fragment.viewpager.isUserInputEnabled = false

        fragment.imageElasticView.setOnClickListener {
            val currentItem = fragment.viewpager.currentItem
            if (currentItem != 3) {
                fragment.viewpager.currentItem = currentItem + 1
            } else if (currentItem == 2) {
                fragment.text.text = getString(R.string.getstart)
                startActivity(Intent(requireContext(), BasicActivity::class.java))
            }
        }

        fragment.viewpager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                if (position == 2) {
                    fragment.imageElasticView.setOnClickListener {
                        activity
                            ?.supportFragmentManager
                            ?.beginTransaction()
                            ?.replace(R.id.fm, Category())
                            ?.commit()
                        onBoardingFinish()
                    }
                    fragment.dotsIndicator.visibility = View.GONE
                    fragment.text.text = getString(R.string.getstart)
                }
            }
        })

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r: Float = 1 - Math.abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        fragment.viewpager.clipToPadding = false
        fragment.viewpager.clipChildren = false
        fragment.viewpager.offscreenPageLimit = 3
        fragment.viewpager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        fragment.viewpager.setPageTransformer(compositePageTransformer)

        return fragment.root
    }

    private fun onBoardingFinish() {
        val shared = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = shared.edit()
        editor.putBoolean("Finished", true)
        editor.apply()
    }

    private fun loadData() {
        list = ArrayList()
        list.add(User(R.drawable.braikingne))
        list.add(User(R.drawable.breaking))
        list.add(User(R.drawable.breaknnnn))

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Framlayout().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}