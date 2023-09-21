package com.selincengiz.dinefeast.ui.cart

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.selincengiz.dinefeast.R
import com.selincengiz.dinefeast.common.Extensions.loadUrl
import com.selincengiz.dinefeast.data.model.Food
import com.selincengiz.dinefeast.data.model.FoodEntity
import com.selincengiz.dinefeast.databinding.ItemCartBinding
import com.selincengiz.dinefeast.databinding.ItemCategoryBinding
import com.selincengiz.dinefeast.ui.search.CategoryAdapter

class CartAdapter(private val itemListener: ItemCartListener) :
    ListAdapter<FoodEntity, CartAdapter.CartViewHolder>(CartDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder =
        CartViewHolder(
            ItemCartBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), itemListener
        )

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) =
        holder.bind(getItem(position))

    class CartViewHolder(
        private val binding: ItemCartBinding,
        private val listener: ItemCartListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: FoodEntity) = with(binding) {

            ivFood.loadUrl(food.imageOne)
            tvTitleFood.text = food.title

            when (food.saleState) {
                true -> {
                    tvPrice.text = food.salePrice.toString()
                }

                false -> {
                    tvPrice.text = food.price.toString()

                }

                else -> {

                }
            }

            btnDelete.setOnClickListener {
                listener.onDeleteClicked(food)
            }

            root.setOnClickListener {
                listener.onClicked(food)
            }
        }

    }

    class CartDiffCallBack() : DiffUtil.ItemCallback<FoodEntity>() {
        override fun areItemsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
            return oldItem == newItem
        }

    }


}

interface ItemCartListener {
    fun onClicked(food: FoodEntity)
    fun onDeleteClicked(food: FoodEntity)
}
