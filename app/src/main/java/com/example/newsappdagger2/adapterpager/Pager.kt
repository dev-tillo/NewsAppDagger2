package com.example.newsappdagger2.adapterpager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsappdagger2.models.User
import com.example.newsappdagger2.pager.BlankSlide


class Pager(fm: Fragment, var list: List<User>) :
    FragmentStateAdapter(fm) {

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return BlankSlide.newInstance(list[position])
    }
}