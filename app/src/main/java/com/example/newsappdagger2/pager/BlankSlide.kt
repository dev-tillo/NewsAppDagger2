package com.example.newsappdagger2.pager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.newsappdagger2.adapters.CategoryrvAdapter
import com.example.newsappdagger2.databinding.FragmentBlankSlideBinding
import com.example.newsappdagger2.models.User

private const val ARG_PARAM1 = "param1"

class BlankSlide : Fragment() {
    private var param1: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getSerializable(ARG_PARAM1) as User
        }
    }

    private lateinit var fragment: FragmentBlankSlideBinding
    private lateinit var categoryrvAdapter: CategoryrvAdapter
    private lateinit var list: ArrayList<User>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentBlankSlideBinding.inflate(inflater, container, false)

        fragment.image.setImageResource(param1?.image!!)

        return fragment.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: User) =
            BlankSlide().apply {
                arguments = Bundle().apply {
                    putSerializable(ARG_PARAM1, param1)
                }
            }
    }
}