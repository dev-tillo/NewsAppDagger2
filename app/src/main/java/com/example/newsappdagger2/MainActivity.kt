package com.example.newsappdagger2

import android.content.Context
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.DimenRes
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.newsappdagger2.adapterpager.Pager
import com.example.newsappdagger2.databinding.ActivityMainBinding
import com.example.newsappdagger2.fragments.bottom.Home
import com.example.newsappdagger2.pager.Framlayout
import java.lang.Math.abs

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var pageradapte: Pager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.apply {
            this@MainActivity
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fm, Framlayout())
                .commit()
        }
    }
}