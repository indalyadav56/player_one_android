package com.example.playerone.fragments.image_view.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.example.playerone.R
import com.example.playerone.databinding.FragmentImageViewBinding
import com.example.playerone.databinding.FragmentVideoBinding
import com.google.android.material.bottomnavigation.BottomNavigationView


class ImageViewFragment : Fragment() {
    private var _binding: FragmentImageViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImageViewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavBar.isVisible = false


        val imageUrl = arguments?.get("image_url")
        Glide.with(requireContext()).asBitmap().load(imageUrl).into(binding.imageView)

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavBar.isVisible = true
    }

}