package com.selincengiz.dinefeast.ui.splash

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.selincengiz.dinefeast.databinding.FragmentSplashBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class SplashFragment : Fragment() {
    @Inject
    lateinit var auth: FirebaseAuth

    private lateinit var binding:FragmentSplashBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= DataBindingUtil.inflate(inflater,
            com.selincengiz.dinefeast.R.layout.fragment_splash, container, false)
        binding.splashFragmentFunctions = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //To animate logo
        animationLogo()
    }

    fun animationLogo(){
        val imageView=binding.logo
        val animationSet = AnimationSet(true)

        val fadeInAnimation: Animation = AnimationUtils.loadAnimation(requireContext(), com.selincengiz.dinefeast.R.anim.fade_in)
        val scaleAnimation: Animation = AnimationUtils.loadAnimation(requireContext(), com.selincengiz.dinefeast.R.anim.scale)

        animationSet.addAnimation(fadeInAnimation)
        animationSet.addAnimation(scaleAnimation)


        fadeInAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {

            }

            override fun onAnimationEnd(animation: Animation) {
                if (auth.currentUser != null) {
                    findNavController().navigate(SplashFragmentDirections.splashToHome())
                }
                else{
                    findNavController().navigate(SplashFragmentDirections.splashToSignIn())
                }
            }

            override fun onAnimationRepeat(animation: Animation) {
                // Animasyon tekrarlandığında yapılacak işlemler
            }
        })

        imageView.startAnimation(animationSet)
    }


}