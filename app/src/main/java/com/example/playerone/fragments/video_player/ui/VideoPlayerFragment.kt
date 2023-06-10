package com.example.playerone.fragments.video_player.ui

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.playerone.R
import com.example.playerone.databinding.FragmentVideoPlayerBinding
import com.example.playerone.utils.Utils
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView


class VideoPlayerFragment : Fragment() {
    private var _binding: FragmentVideoPlayerBinding? = null
    private val binding get() = _binding!!

    private lateinit var utils: Utils
    private lateinit var player: ExoPlayer

    private lateinit var appBarLayout: AppBarLayout


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentVideoPlayerBinding.inflate(inflater,container,false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        utils = Utils()

        utils.fullscreen(requireActivity())

        utils.hideBottomNavigation(requireActivity())

        appBarLayout = requireActivity().findViewById<AppBarLayout>(R.id.appBarLayout)
        appBarLayout.layoutParams.height = 0


        activity?.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR;

        player = ExoPlayer.Builder(requireContext()).build()
        binding.playerView.player = player

        val videoURI: String? = arguments?.getString("videoUri")
        val mediaItem = videoURI?.let { MediaItem.fromUri(it) }
        if (mediaItem != null) {
            player.setMediaItem(mediaItem)
            player.prepare()
            player.play()
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        player.release()
        _binding = null
        val bottomNavBar = requireActivity().findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavBar.isVisible = true

    }

}