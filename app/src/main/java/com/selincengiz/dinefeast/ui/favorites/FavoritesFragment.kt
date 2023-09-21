package com.selincengiz.dinefeast.ui.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.selincengiz.dinefeast.R
import com.selincengiz.dinefeast.common.HomeState
import com.selincengiz.dinefeast.data.model.FoodEntity
import com.selincengiz.dinefeast.databinding.FragmentFavoritesBinding
import com.selincengiz.dinefeast.ui.adapter.FoodAdapter
import com.selincengiz.dinefeast.ui.adapter.ItemListener
import com.selincengiz.dinefeast.ui.home.HomeFragmentDirections
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoritesFragment : Fragment(), ItemListener {

    private lateinit var binding:FragmentFavoritesBinding
    private val viewModel by viewModels<FavoritesViewModel>()
    private val adapter by lazy { FoodAdapter(this) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=DataBindingUtil.inflate(inflater,R.layout.fragment_favorites, container, false)
        binding.favoritesRecycler.adapter=adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        viewModel.getFoods()
    }

    fun observe(){
        viewModel.listFavorite.observe(viewLifecycleOwner){
              adapter.submitList(it)

            }
        }

    override fun onClicked(food: FoodEntity) {
        food.id?.let {
            findNavController().navigate(FavoritesFragmentDirections.favoritesToDetail(it))

        } ?: kotlin.run {
            Toast.makeText(requireContext(), "Could not find food", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onAddClicked(food: FoodEntity) {
        food.id?.let {
            viewModel.addCart(it)
            findNavController().navigate(FavoritesFragmentDirections.favoritesToCart())

        } ?: kotlin.run {
            Toast.makeText(requireContext(), "Addition is failed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun favoriteClicked(food: FoodEntity) {

        viewModel.addFood(food)
        viewModel.getFoods()
    }

    override fun unFavoriteClicked(food: FoodEntity) {
        viewModel.deleteFood(food)
        viewModel.getFoods()
    }

}


