package com.example.newsappdagger2.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.newsappdagger2.R
import com.example.newsappdagger2.classses.newapi.Article
import com.example.newsappdagger2.databace.entity.UserEntity
import com.example.newsappdagger2.databinding.ItemCategoryBinding
import com.example.newsappdagger2.databinding.ItemRvHomeBinding
import com.squareup.picasso.Picasso

class HomeRvAdapter(var context: Context, var listener: onClick) :
    ListAdapter<Article, HomeRvAdapter.MyViewHolder>(MyDiffutils()) {

    class MyDiffutils : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.source.id == newItem.source.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    class MyViewHolder(var binding: ItemRvHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemRvHomeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageCLass = getItem(position)
        holder.binding.apply {

            Glide.with(context)
                .load(imageCLass.urlToImage)
                .placeholder(R.drawable.ic_launcher_foreground)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .into(image)

            name.text = imageCLass.title
            desc.text = imageCLass.description

            politick.setOnClickListener {
                listener.Click(imageCLass, position)
            }
        }
    }

    interface onClick {
        fun Click(imageCLass: Article, position: Int)
    }
}
