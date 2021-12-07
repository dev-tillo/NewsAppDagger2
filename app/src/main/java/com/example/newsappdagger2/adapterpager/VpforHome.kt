package com.example.newsappdagger2.adapterpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsappdagger2.fragments.Slide

class VpforHome(fm: Fragment, var list: List<String>) : FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return Slide.newInstance(list[position])
    }
}