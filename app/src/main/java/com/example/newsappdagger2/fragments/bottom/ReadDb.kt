package com.example.newsappdagger2.fragments.bottom

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.newsappdagger2.App
import com.example.newsappdagger2.R
import com.example.newsappdagger2.adapters.DbRv
import com.example.newsappdagger2.databace.AppDatabaceNews
import com.example.newsappdagger2.databace.entity.UserEntity
import com.example.newsappdagger2.databinding.FragmentReadDbBinding
import com.example.newsappdagger2.fragments.Info
import com.example.newsappdagger2.fragments.Info2Db
import javax.inject.Inject

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ReadDb : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appCompanent.injectdb(this)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private lateinit var fragment: FragmentReadDbBinding
    private lateinit var dbRv: DbRv

    @Inject
    lateinit var appDatabaceNews: AppDatabaceNews

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragment = FragmentReadDbBinding.inflate(inflater, container, false)

        dbRv = DbRv(requireContext(), object : DbRv.Onclick {
            override fun onClickDb(userEntity: UserEntity, position: Int) {
                val fm: Fragment = Info2Db.newInstance(userEntity)
                val bundle = Bundle()
                bundle.putSerializable("url", userEntity)
                fm.arguments = bundle

                findNavController().navigate(R.id.info2Db)
            }
        })
        dbRv.notifyDataSetChanged()
        fragment.rvc.adapter = dbRv
        val list1 = appDatabaceNews.newsDao().getList()
        if (list1.isNotEmpty()) {
            fragment.rvc.visibility = View.VISIBLE
            fragment.tv.visibility = View.GONE

            list1.forEach {
                if (list1[0].like == true) {
                    dbRv.submitList(list1)
                } else if (list1[0].like == false) {
                    dbRv.submitList(emptyList())
                }
            }
        } else {
            fragment.rvc.visibility = View.GONE
            fragment.tv.visibility = View.VISIBLE
        }
        return fragment.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ReadDb().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}