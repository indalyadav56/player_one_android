package com.example.playerone.fragments.audio.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.playerone.R
import com.example.playerone.databinding.FragmentAudioBinding
import com.example.playerone.fragments.audio.adapters.AudioAdapter
import com.example.playerone.fragments.audio.models.AudioModel
import com.example.playerone.fragments.audio.viewmodels.AudioViewModel
import com.example.playerone.fragments.audio_player.ui.AudioPlayerFragment
import com.example.playerone.utils.ModalBottomSheet


class AudioFragment : Fragment() {

    private var _binding: FragmentAudioBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AudioViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAudioBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.fetchAudios()

        val adapter = AudioAdapter(requireContext(),::onAudioClick,::onAudioMenuClicked)

        binding.audioRV.adapter = adapter
        binding.audioRV.layoutManager = LinearLayoutManager(requireContext())

        viewModel.audios.observe(viewLifecycleOwner, Observer {
            adapter.setVideoList(it)
            adapter.notifyDataSetChanged()

        })

        // swipe refresh layout
        binding.mSwipeRefreshLayout.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.mSwipeRefreshLayout.isRefreshing = false
                adapter.notifyDataSetChanged()

            }, 1000)
        }
    }

    private fun onAudioClick(audioModel: AudioModel){

        val bundle = Bundle()
        bundle.putString("audio_title",audioModel.title)
        bundle.putString("audio_path",audioModel.path)
        bundle.putString("audio_art",audioModel.artUri.toString())
        findNavController().navigate(R.id.action_audioFragment_to_audioPlayerFragment,bundle)

    }

    private fun onAudioMenuClicked(audioModel: AudioModel){
        val modalBottomSheet = ModalBottomSheet()
        modalBottomSheet.show(requireActivity().supportFragmentManager, ModalBottomSheet.TAG)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}