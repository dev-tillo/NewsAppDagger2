package com.example.newsappdagger2.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsappdagger2.R
import com.example.newsappdagger2.databinding.FragmentLanguageBinding
import com.example.newsappdagger2.fragments.bottom.User
import com.example.newsappdagger2.utils.MySharedPreference
import com.yariksoffice.lingver.Lingver

class LanguageFragment : Fragment() {

    private lateinit var fragment: FragmentLanguageBinding
    private var counter2 = 0
    private var counter3 = 0
    private lateinit var root: View
    private lateinit var mySharedPreference: MySharedPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (activity as BasicActivity?)?.hideBlurView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentLanguageBinding.inflate(inflater, container, false)
        root = fragment.root

        mySharedPreference = MySharedPreference(requireContext())

        fragment.turkish.setOnCheckedChangeListener { compoundButton, b ->
            fragment.name.text = getString(R.string.language)
        }

        fragment.turkish.isChecked = mySharedPreference.getPreferences("lang") != "de"

        fragment.apply {

            turkish.setOnClickListener {
                when (counter2) {
                    0 -> {
                        mySharedPreference.setPreferences("lang", "tr")
                        counter3--
                        turkish.setBackgroundResource(R.drawable.vavv)
                        chek1.visibility = View.VISIBLE
                        counter2++
                        Lingver.getInstance().setLocale(
                            requireContext(),
                            mySharedPreference.getPreferences("lang") ?: "tr"
                        )
                    }
                    1 -> {
                        mySharedPreference.setPreferences("lang", "de")
                        turkish.setBackgroundResource(R.drawable.vaccc)
                        chek1.visibility = View.GONE
                        counter2--
                        counter3++
                    }
                }
            }

            german.setOnClickListener {
                when (counter3) {
                    0 -> {
                        mySharedPreference.setPreferences("lang", "de")
                        german.setBackgroundResource(R.drawable.vavv)
                        chek3.visibility = View.VISIBLE
                        counter3++
                        counter2--
                        Lingver.getInstance().setLocale(
                            requireContext(),
                            mySharedPreference.getPreferences("lang") ?: "de"
                        )

                    }
                    1 -> {
                        mySharedPreference.setPreferences("lang", "tr")
                        german.setBackgroundResource(R.drawable.vaccc)
                        chek3.visibility = View.GONE
                        counter3--
                        counter2++
                    }
                }
            }
            left.setOnClickListener {
                findNavController().popBackStack()
            }
        }
        return fragment.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (activity as BasicActivity?)?.showBlurView()
    }
}