package com.selincengiz.dinefeast.ui.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.selincengiz.dinefeast.R
import com.selincengiz.dinefeast.common.Extensions.loadUrl
import com.selincengiz.dinefeast.data.mapper.mapToFavorites
import com.selincengiz.dinefeast.data.mapper.mapToFood
import com.selincengiz.dinefeast.data.model.Food
import com.selincengiz.dinefeast.data.model.FoodEntity
import com.selincengiz.dinefeast.databinding.ItemFoodBinding


class FoodAdapter(private val itemListener: ItemListener) :
    ListAdapter<FoodEntity, FoodAdapter.FoodViewHolder>(FoodDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder =
        FoodViewHolder(
            ItemFoodBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), itemListener
        )

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) =
        holder.bind(getItem(position))

    class FoodViewHolder(
        private val binding: ItemFoodBinding,
        private val listener: ItemListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(food: FoodEntity) = with(binding) {

            ivFood.loadUrl(food.imageOne)
            tvTitleFood.text = food.title
            food.isFavorite?.let {
                if (it) {
                    ivFavorite.setBackgroundResource(R.drawable.baseline_favorite_24)
                } else {
                    ivFavorite.setBackgroundResource(R.drawable.baseline_favorite_border_24)
                }
            }


            when (food.saleState) {
                true -> {
                    tvPrice.text = food.salePrice.toString()
                    tvSale.text = food.price.toString()
                    tvSale.paintFlags = tvSale.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                    tvSale.visibility = View.VISIBLE
                }

                false -> {
                    tvPrice.text = food.price.toString()
                    tvSale.visibility = View.GONE
                }

                else -> {

                }
            }

            btnAdd.setOnClickListener {
                listener.onAddClicked(food)

            }

            root.setOnClickListener {
                listener.onClicked(food)
            }

            ivFavorite.setOnClickListener {
                food.isFavorite?.let {
                    if (it) {
                        listener.unFavoriteClicked(food)

                    } else {
                        listener.favoriteClicked(food.mapToFavorites(true))

                    }
                    notifyChange()
                }


            }
        }

    }

    class FoodDiffCallBack() : DiffUtil.ItemCallback<FoodEntity>() {
        override fun areItemsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FoodEntity, newItem: FoodEntity): Boolean {
            return oldItem == newItem
        }

    }


}

interface ItemListener {
    fun onClicked(food: FoodEntity)
    fun onAddClicked(food: FoodEntity)
    fun favoriteClicked(food: FoodEntity)
    fun unFavoriteClicked(food: FoodEntity)
}
