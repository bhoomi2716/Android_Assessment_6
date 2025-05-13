package com.example.assessment_6.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.assessment_6.Model.ModelCategory
import com.example.assessment_6.R
import com.example.assessment_6.databinding.DesignCategoryBinding

class AdapterCategory(
    private var categories: MutableList<ModelCategory>,
    private val onClick: (ModelCategory) -> Unit
) : RecyclerView.Adapter<AdapterCategory.CategoryViewHolder>() {

    inner class CategoryViewHolder(val binding: DesignCategoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(DesignCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.binding.tvShowCategoryName.text = category.name
        // Set category icon based on position (or better: based on data)
        when (position) {
            0 -> holder.binding.ivCategory.setImageResource(R.drawable.motivation_icon)
            1 -> holder.binding.ivCategory.setImageResource(R.drawable.beauty_icon_category)
            2 -> holder.binding.ivCategory.setImageResource(R.drawable.happy_icon_category)
            3 -> holder.binding.ivCategory.setImageResource(R.drawable.music_icon_category)
            4 -> holder.binding.ivCategory.setImageResource(R.drawable.health_icon_category)
            else -> holder.binding.ivCategory.setImageResource(R.drawable.add_category_icon) // fallback
        }

        // Set click listener
        holder.binding.root.setOnClickListener {
            onClick(category)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<ModelCategory>) {
        categories.clear()
        categories.addAll(newList)
        notifyDataSetChanged()
    }
}
