package com.example.newsappdagger2.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.newsappdagger2.R
import com.example.newsappdagger2.databace.entity.UserEntity
import com.example.newsappdagger2.databinding.ItemRvHomeBinding

class DbRv(
    var context: Context,
    var listener: Onclick
) :
    ListAdapter<UserEntity, DbRv.MyViewHolder>(MyDiffutils()) {

    class MyDiffutils : DiffUtil.ItemCallback<UserEntity>() {
        override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean {
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

            Glide.with(context).load(imageCLass.urlToImage)
                .placeholder(R.drawable.ic_launcher_foreground).into(image)

            name.text = imageCLass.title
            desc.text = imageCLass.description

            politick.setOnClickListener {
                listener.onClickDb(imageCLass, position)
            }
        }
    }

    interface Onclick {
        fun onClickDb(userEntity: UserEntity, position: Int)
    }
}