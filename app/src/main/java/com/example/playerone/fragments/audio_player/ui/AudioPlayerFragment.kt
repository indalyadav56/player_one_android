package com.example.playerone.fragments.audio_player.ui

import android.Manifest
import android.annotation.SuppressLint
import android.content.*
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.*
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.playerone.R
import com.example.playerone.constants.Constants
import com.example.playerone.databinding.FragmentAudioPlayerBinding
import com.example.playerone.fragments.audio_player.utils.MusicNotification
import com.example.playerone.fragments.audio_player.utils.MusicService
import com.example.playerone.fragments.audio_player.viewmodels.AudioPlayerViewModel
import com.example.playerone.utils.Utils
import com.google.android.material.appbar.AppBarLayout


class AudioPlayerFragment : Fragment(), MediaPlayer.OnCompletionListener {

    private var _binding: FragmentAudioPlayerBinding? = null
    private val binding get() = _binding!!

    private lateinit var runnable: Runnable

    private lateinit var appBarLayout: AppBarLayout
    private lateinit var musicService: MusicService

    private val viewModel: AudioPlayerViewModel by viewModels()


    companion object{
//        lateinit var mediaPlayer: MediaPlayer
        var isPlaying: Boolean = false
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAudioPlayerBinding.inflate(inflater, container, false)
        if (isPlaying){
            musicService.mediaPlayer!!.pause()
        }
        return binding.root
    }

    @SuppressLint("Recycle", "UseCompatLoadingForDrawables", "UnspecifiedImmutableFlag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        musicService = MusicService()

        musicService.mediaPlayer = MediaPlayer()

        Utils().hideBottomNavigation(requireActivity())

        appBarLayout = requireActivity().findViewById<AppBarLayout>(R.id.appBarLayout)
        appBarLayout.layoutParams.height = 0

        val audioPath: String? = arguments?.getString("audio_path")
        val audioArt: String? = arguments?.getString("audio_art")
        val audioTitle: String? = arguments?.getString("audio_title")

        binding.currentAudioTitle.text = audioTitle
        binding.currentAudioTitle.isSelected = true

        if (audioArt != null) setAudioThumbnail(audioArt)

        if (audioPath != null) {
            val musicNotification = MusicNotification()
            if (audioTitle != null) {
                if (audioArt != null) {
                    musicNotification.initMusicNotification(requireContext(),audioPath,audioTitle,audioArt)
                }
            }
//            mediaPlayer.reset()
//            mediaPlayer.setDataSource(requireContext(), Uri.parse(audioPath))
//            mediaPlayer.prepare()
//            mediaPlayer.start()

            val serviceIntent = Intent(requireContext(), MusicService::class.java)
            serviceIntent.putExtra("audioPath", audioPath)
            requireContext().startService(serviceIntent)

            isPlaying = true
        }

        binding.seekBar.progress = 0
        binding.seekBar.max = musicService.mediaPlayer!!.duration
        musicService.mediaPlayer!!.setOnCompletionListener(this)

//        seekbar
        if (musicService.mediaPlayer!!.isPlaying) {
            binding.seekBar.max = musicService.mediaPlayer!!.duration
        }
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicService.mediaPlayer!!.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
        seekBarSetup()

//        play pause button
        binding.playPauseBtn.setOnClickListener {
            if (musicService.mediaPlayer!!.isPlaying) pauseMusic() else playMusic()
        }

        binding.backBtn.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun playMusic() {
        musicService.mediaPlayer!!.start()
        binding.playPauseBtn.setImageResource(R.drawable.ic_baseline_play_circle_24)
    }

    private fun pauseMusic() {
        musicService.mediaPlayer!!.pause()
        binding.playPauseBtn.setImageResource(R.drawable.ic_baseline_pause_circle_24)
    }

    private fun prevMusic() {}

    private fun nextMusic() {}

    private fun seekBarSetup() {
        runnable = Runnable {
            binding.seekBar.progress = musicService.mediaPlayer!!.currentPosition
            Handler(Looper.getMainLooper()).postDelayed(runnable, 200)
        }
        Handler(Looper.getMainLooper()).postDelayed(runnable, 0)
    }

    private fun setAudioThumbnail(audioArt: String) {
        Glide.with(requireContext())
            .load(audioArt)
            .apply(RequestOptions().placeholder(R.drawable.ic_baseline_play_circle_24).centerCrop())
            .into(binding.imageView)
    }

    @Deprecated("Deprecated in Java")
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.REQUEST_CODE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission was granted
                Toast.makeText(requireContext(), "Permission Granted!!", Toast.LENGTH_SHORT).show()
            } else {
                // Permission was denied
                Toast.makeText(requireContext(), "Permission is not granted!", Toast.LENGTH_SHORT)
                    .show()
                Utils().requestPermission(
                    requireActivity(), Manifest.permission.READ_EXTERNAL_STORAGE,
                    Constants.REQUEST_CODE_PERMISSION
                )
            }
        }
    }

    override fun onCompletion(p0: MediaPlayer?) {
        Toast.makeText(requireContext(),"Song Completed!",Toast.LENGTH_SHORT).show()
    }


    override fun onStart() {
        super.onStart()
//        val intent = Intent(requireContext(), MusicService::class.java)
//        requireActivity().bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onStop() {
        super.onStop()
//        if (serviceBound) {
//            requireActivity().unbindService(serviceConnection)
//            serviceBound = false
//        }
    }


    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
//        _binding = null
        Utils().showBottomNavigation(requireActivity())
        appBarLayout.layoutParams.height = 155
    }

}