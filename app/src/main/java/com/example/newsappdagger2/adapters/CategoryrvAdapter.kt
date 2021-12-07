package com.example.newsappdagger2.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.newsappdagger2.databace.firstcategory.AppDatabaceCategory
import com.example.newsappdagger2.databace.firstcategory.CategoryEntity
import com.example.newsappdagger2.databinding.ItemCategoryBinding

class CategoryrvAdapter(
    private var context: Context,
    private var list: ArrayList<CategoryEntity>, var listener: onClick
) :
    RecyclerView.Adapter<CategoryrvAdapter.MyViewHolder>() {

    class MyViewHolder(var binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    private var appDatabase: AppDatabaceCategory = AppDatabaceCategory.getInstance(context)

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val imageCLass = list[position]
        holder.binding.apply {
            name.text = imageCLass.name

            if (imageCLass.like) {
                name.setTextColor(Color.parseColor("#FFFFFF"))
                constraint.setBackgroundColor(Color.parseColor("#475AD7"))
            } else {
                name.setTextColor(Color.parseColor("#000000"))
                constraint.setBackgroundColor(Color.parseColor("#F3F4F6"))
            }

            constraint.setOnClickListener {
                if (!imageCLass.like) {
                    imageCLass.like = true
                    name.setTextColor(Color.parseColor("#FFFFFF"))
                    constraint.setBackgroundColor(Color.parseColor("#475AD7"))
                    appDatabase.categorydb().addDbCategory(imageCLass)
                    Toast.makeText(root.context, "like", Toast.LENGTH_SHORT).show()
                } else if (imageCLass.like) {
                    imageCLass.like = false
                    name.setTextColor(Color.parseColor("#000000"))
                    constraint.setBackgroundColor(Color.parseColor("#F3F4F6"))
                    appDatabase.categorydb().deletBYId(imageCLass.id)
                    Toast.makeText(root.context, "dislike", Toast.LENGTH_SHORT).show()
                }
            }
            listener.Click(imageCLass, position)
        }
    }

    override fun getItemCount(): Int = list.size

    interface onClick {
        fun Click(imageCLass: CategoryEntity, position: Int)
    }
}
