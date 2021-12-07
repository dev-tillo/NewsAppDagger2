package com.example.newsappdagger2.fragments

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.newsappdagger2.R
import com.example.newsappdagger2.databinding.ActivityBasicBinding


class BasicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBasicBinding
    private lateinit var navController: NavController


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        navController = findNavController(R.id.my_nav_host_fragment)

        binding.navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.info2 -> {
                    hideBlurView()
                }
                R.id.info2Db -> {
                    hideBlurView()
                }
                R.id.searchFragment -> {
                    hideBlurView()
                }
                else -> showBlurView()
            }
        }
    }


    fun hideBlurView() {
        binding.navView.visibility = View.GONE
    }

    fun showBlurView() {
        binding.navView.visibility = View.VISIBLE
    }
}