package com.example.playerone.fragments.video.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playerone.R
import com.example.playerone.constants.Constants
import com.example.playerone.databinding.FragmentVideoBinding
import com.example.playerone.fragments.video.adapters.VideoAdapter
import com.example.playerone.fragments.video.models.VideoModel
import com.example.playerone.fragments.video.viewmodels.VideoViewModel
import com.example.playerone.utils.ModalBottomSheet
import com.example.playerone.utils.Utils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.coroutines.launch



class VideoFragment : Fragment(), VideoAdapter.VideoItemClick {

    private var _binding: FragmentVideoBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VideoViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Utils().requestPermission(
            requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Constants.REQUEST_CODE_PERMISSION
        )


        val adapter = VideoAdapter(requireContext(), this)
        binding.videoRecyclerView.adapter = adapter
        binding.videoRecyclerView.layoutManager = LinearLayoutManager(requireContext())


        viewModel.videos.observe(viewLifecycleOwner, Observer { videos ->
            binding.videoProgressBar.isVisible = true
            if (videos.size > 0) {
                adapter.setVideoList(videos)
                adapter.notifyDataSetChanged()
                binding.videoProgressBar.isVisible = false
            }

            // swipe refresh layout
            binding.mSwipeRefreshLayout.setOnRefreshListener {
                Handler(Looper.getMainLooper()).postDelayed({
                    binding.mSwipeRefreshLayout.isRefreshing = false
                    adapter.notifyDataSetChanged()

                }, 1000)

            }

        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchVideos(arguments?.getString("folderName").toString())
        }


//        val adapter = VideoAdapter(requireContext(),videoList,this)
//        binding.videoRecyclerView.adapter = adapter
//        binding.videoRecyclerView.layoutManager = LinearLayoutManager(requireContext())

    }


    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == Constants.REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                Toast.makeText(requireContext(), "Permission Granted!!", Toast.LENGTH_SHORT).show()
            } else {
                // Permission was denied
                Toast.makeText(requireContext(), "Permission is not granted!", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onClick(videoModel: VideoModel) {
        val bundle = Bundle()
        bundle.putString("videoUri", videoModel.path)
        findNavController().navigate(R.id.action_videoFragment_to_videoPlayerFragment, bundle)

    }

    override fun onBtnClicked(position: Int) {
        val modalBottomSheet = ModalBottomSheet()
        modalBottomSheet.show(requireActivity().supportFragmentManager, ModalBottomSheet.TAG)
    }

    override fun onLongClick(position: Int) {
        Toast.makeText(requireContext(), "On Long Click $position", Toast.LENGTH_SHORT).show()
        Utils().requestPermission(
            requireActivity(),
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Constants.REQUEST_CODE_PERMISSION
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}