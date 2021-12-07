package com.example.newsappdagger2.fragments.bottom

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.example.newsappdagger2.R
import com.example.newsappdagger2.databinding.FragmentUserBinding
import com.example.newsappdagger2.fragments.LanguageFragment
import com.example.newsappdagger2.utils.MySharedPreference
import com.example.newsappdagger2.utils.ThemeHelper


class User : Fragment() {

    private lateinit var fragment: FragmentUserBinding
    private lateinit var mySharedPreference: MySharedPreference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentUserBinding.inflate(inflater, container, false)
        fragment.apply {

            mySharedPreference = MySharedPreference(requireContext())

            if (mySharedPreference.getPreferences("isDark") == "1") {
                ThemeHelper.applyTheme(ThemeHelper.darkMode)
                fragment.switchButton.isChecked = true
            } else {
                ThemeHelper.applyTheme(ThemeHelper.lightMode)
                fragment.switchButton.isChecked = false
            }

            fragment.switchButton.setOnCheckedChangeListener { compoundButton, b ->
                if (b) {
                    mySharedPreference.setPreferences("isDark", "1")
                    ThemeHelper.applyTheme(ThemeHelper.darkMode)
                } else {
                    ThemeHelper.applyTheme(ThemeHelper.lightMode)
                    mySharedPreference.setPreferences("isDark", "0")
                }
            }
            language.setOnClickListener {
                findNavController().navigate(R.id.languageFragment)
            }
        }
        return fragment.root
    }
}