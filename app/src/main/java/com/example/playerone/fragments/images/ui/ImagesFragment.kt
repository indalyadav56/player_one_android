package com.example.playerone.fragments.images.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.playerone.R
import com.example.playerone.databinding.FragmentAudioBinding
import com.example.playerone.databinding.FragmentImagesBinding
import com.example.playerone.fragments.images.adapters.ImagesAdapter
import com.example.playerone.fragments.images.api.ImagesAPI
import com.example.playerone.fragments.images.repository.ImagesRepository
import com.example.playerone.fragments.images.viewmodels.ImageViewModelFactory
import com.example.playerone.fragments.images.viewmodels.ImagesViewModel
import com.example.playerone.retrofit.RetrofitHelper
import com.example.playerone.utils.NetworkResult
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.playerone.fragments.images.models.ImageModel
import com.example.playerone.fragments.images.models.ImageModelItem
import com.google.android.material.bottomnavigation.BottomNavigationView


class ImagesFragment : Fragment(),ImagesAdapter.OnImageItemClicked {

    private var _binding: FragmentImagesBinding? = null
    private val binding get() = _binding!!

    private val imageAPI = RetrofitHelper.getInstance().create(ImagesAPI::class.java)
    private val viewModel: ImagesViewModel by viewModels{ ImageViewModelFactory(ImagesRepository(imageAPI)) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentImagesBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllImages()

        val adapter = ImagesAdapter(requireContext(),this)
        binding.imagesRV.adapter = adapter
        binding.imagesRV.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)


        viewModel.imageResponseLiveData.observe(viewLifecycleOwner, Observer {
            binding.progressBar.isVisible = false
            when (it){
                is NetworkResult.Loading ->{
                    binding.progressBar.isVisible = true
                }
                is NetworkResult.Success -> {
                    binding.progressBar.isVisible = false
                    it.data?.let { it1 -> adapter.setImageResponse(it1)}
                    adapter.notifyDataSetChanged()
                }
                is NetworkResult.Error ->{
                    binding.progressBar.isVisible = false
                    Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                }

                else -> {
                    Toast.makeText(requireContext(),"Error",Toast.LENGTH_SHORT).show()
                }
            }
        })


    }

    override fun onClick(imageModelItem: ImageModelItem) {
        val bundle:Bundle = Bundle()
        bundle.putString("image_url",imageModelItem.download_url)
        findNavController().navigate(R.id.action_imagesFragment_to_imageViewFragment,bundle)

    }

    override fun onLongClick() {

    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}