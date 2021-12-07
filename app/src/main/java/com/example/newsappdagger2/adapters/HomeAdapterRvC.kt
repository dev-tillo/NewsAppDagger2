package com.example.newsappdagger2.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsappdagger2.R
import com.example.newsappdagger2.classses.newapi.Article
import com.example.newsappdagger2.databace.AppDatabaceNews
import com.example.newsappdagger2.databace.entity.UserEntity
import com.example.newsappdagger2.databace.firstcategory.AppDatabaceCategory
import com.example.newsappdagger2.databinding.ItempagerBinding

class HomeAdapterRvC(
    var context: Context,
    var listener: onclickkk,
    var appDatabaceNews: AppDatabaceNews
) :
    ListAdapter<Article, HomeAdapterRvC.Vh>(MyDiffUtill()) {

    class MyDiffUtill : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.source.id == newItem.source.id
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    class Vh(var binding: ItempagerBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    interface onclickkk {
        fun Click(imageCLass: Article, position: Int)
        fun clickbookmark(imageCLass: Article, position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItempagerBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        val item = getItem(position)
        holder.binding.apply {

            Glide.with(context).load(item.urlToImage)
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(image)

            category.text = item.author
            desc.text = item.description

            elastick.setOnClickListener {
                listener.Click(item, position)
            }

            bookmark.setOnClickListener {
                if (!item.like) {
                    item.like = true
                    bookmark.setImageResource(R.drawable.bookmarkkkk)
                } else if (item.like) {
                    item.like = false
                    bookmark.setImageResource(R.drawable.bookmark)
                }
                listener.clickbookmark(item, position)
            }

            val list = appDatabaceNews.newsDao().getList()
            list.forEach {
                if (it.author == item.author) {
                    bookmark.setImageResource(R.drawable.bookmarkkkk)
                } else if (it.author != item.author) {
                    bookmark.setImageResource(R.drawable.bookmark)
                }
            }

        }
    }
}