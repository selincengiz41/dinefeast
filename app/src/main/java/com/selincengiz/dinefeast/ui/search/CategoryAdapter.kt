package com.selincengiz.dinefeast.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.selincengiz.dinefeast.R
import com.selincengiz.dinefeast.databinding.ItemCategoryBinding

class CategoryAdapter(private val itemListener: ItemCategoryListener) :
    ListAdapter<String, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder =
        CategoryViewHolder(
            ItemCategoryBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), itemListener
        )

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) =
        holder.bind(getItem(position))

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding,
        private val listener: ItemCategoryListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: String) = with(binding) {

            when (category) {

                "Pizza" -> {
                    ivCategory.setImageResource(R.drawable.cat_1)

                }

                "Burger" -> {
                    ivCategory.setImageResource(R.drawable.cat_2)
                }

                "Sides" -> {
                    ivCategory.setImageResource(R.drawable.fries)
                }

                "Drinks" -> {
                    ivCategory.setImageResource(R.drawable.cat_4)

                }

                "Pasta" -> {
                    ivCategory.setImageResource(R.drawable.pasta)

                }
            }

            tvCategory.text = category

            root.setOnClickListener {
                listener.onClicked(category)
            }
        }

    }

    class CategoryDiffCallBack() : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem == newItem
        }

    }


}

interface ItemCategoryListener {
    fun onClicked(category: String)
}
