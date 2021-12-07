package com.example.newsappdagger2.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsappdagger2.adapters.CategoryrvAdapter
import com.example.newsappdagger2.databace.firstcategory.AppDatabaceCategory
import com.example.newsappdagger2.databace.firstcategory.CategoryEntity
import com.example.newsappdagger2.databinding.FragmentCategoryBinding

class Category : Fragment() {

    private lateinit var fragment: FragmentCategoryBinding
    private lateinit var categoryrvAdapter: CategoryrvAdapter
    private lateinit var appDatabaceCategory: AppDatabaceCategory
    private lateinit var list: ArrayList<CategoryEntity>
    private lateinit var selectList: List<CategoryEntity>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentCategoryBinding.inflate(inflater, container, false)
        appDatabaceCategory = AppDatabaceCategory.getInstance(requireContext())

        selectList = AppDatabaceCategory.getInstance(requireContext()).categorydb().getLIst()
        loadlist()

        categoryrvAdapter =
            CategoryrvAdapter(
                requireContext(), list, object : CategoryrvAdapter.onClick {
                    override fun Click(imageCLass: CategoryEntity, position: Int) {
                        if (imageCLass.like) {
                            AppDatabaceCategory.getInstance(requireContext()).categorydb()
                                .addDbCategory(imageCLass)
                        } else if (!imageCLass.like) {
                            AppDatabaceCategory.getInstance(requireContext()).categorydb()
                                .deletBYId(imageCLass.id)
                        }
                    }
                })

        fragment.rvc.adapter = categoryrvAdapter

        fragment.imageElasticView.setOnClickListener {
            startActivity(Intent(requireContext(), BasicActivity::class.java))
            activity?.finish()
        }
        return fragment.root
    }

    private fun loadlist() {
        list = ArrayList()

        list.add(CategoryEntity(id = 1, name = "\uD83C\uDFC8 Sports", like = false))
        list.add(CategoryEntity(id = 2, name = "⚖️   Politics", like = false))

        list.add(CategoryEntity(id = 3, name = "\uD83C\uDF1E   Life", like = false))
        list.add(CategoryEntity(id = 4, name = "\uD83C\uDFAE   Gaming", like = false))

        list.add(CategoryEntity(id = 5, name = "\uD83D\uDC3B   Animals", like = false))
        list.add(CategoryEntity(id = 6, name = "\uD83C\uDF34   Nature", like = false))

        list.add(CategoryEntity(id = 7, name = "\uD83C\uDF54   Food", like = false))
        list.add(CategoryEntity(id = 8, name = "\uD83C\uDFA8   Art", like = false))

        list.add(CategoryEntity(id = 9, name = "\uD83D\uDCDC   History", like = false))
        list.add(CategoryEntity(id = 10, name = "\uD83D\uDC57   Fashion", like = false))

        list.add(CategoryEntity(id = 11, name = "\uD83D\uDE37   Covid-19", like = false))
        list.add(CategoryEntity(id = 12, name = "⚔️   Middle East", like = false))

    }
}