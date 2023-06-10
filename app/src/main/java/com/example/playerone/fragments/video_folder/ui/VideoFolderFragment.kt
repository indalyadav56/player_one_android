package com.example.playerone.fragments.video_folder.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playerone.R
import com.example.playerone.databinding.FragmentVideoBinding
import com.example.playerone.databinding.FragmentVideoFolderBinding
import com.example.playerone.fragments.video.adapters.VideoAdapter
import com.example.playerone.fragments.video.models.VideoModel
import com.example.playerone.fragments.video.viewmodels.VideoViewModel
import com.example.playerone.fragments.video_folder.adapters.VideoFolderAdapter
import com.example.playerone.fragments.video_folder.models.VideoFolderModel
import com.example.playerone.fragments.video_folder.viewmodels.VideoFolderViewModel
import com.example.playerone.utils.ModalBottomSheet
import kotlinx.coroutines.launch


class VideoFolderFragment : Fragment(), VideoFolderAdapter.VideoItemClick {

    private var _binding: FragmentVideoFolderBinding? = null
    private val binding get() = _binding!!

    private val viewModel: VideoFolderViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoFolderBinding.inflate(inflater,container,false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.fetchFolders()
        }

        val adapter = VideoFolderAdapter(requireContext(), this)
        binding.videoFolderRV.adapter = adapter
        binding.videoFolderRV.layoutManager = LinearLayoutManager(requireContext())


        viewModel.folders.observe(viewLifecycleOwner, Observer { videos ->
//            binding.videoProgressBar.isVisible = true
            if (videos.isNotEmpty()) {
                adapter.setVideoList(videos)
                adapter.notifyDataSetChanged()
//                binding.videoProgressBar.isVisible = false
            }

//            // swipe refresh layout
//            binding.mSwipeRefreshLayout.setOnRefreshListener {
//                Handler(Looper.getMainLooper()).postDelayed({
//                    binding.mSwipeRefreshLayout.isRefreshing = false
//                    adapter.notifyDataSetChanged()
//
//                }, 1000)

        })
    }


    override fun onClick(videoFolderModel: VideoFolderModel) {
        val bundle = Bundle()
        bundle.putString("folderName",videoFolderModel.folderName)
        findNavController().navigate(R.id.action_videoFolderFragment_to_videoFragment,bundle)
    }

    override fun onBtnClicked(position: Int) {
        val modalBottomSheet = ModalBottomSheet()
        modalBottomSheet.show(requireActivity().supportFragmentManager, ModalBottomSheet.TAG)
    }

    override fun onLongClick(position: Int) {
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



}