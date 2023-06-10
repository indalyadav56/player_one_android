package com.example.playerone.fragments.audio.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.playerone.R
import com.example.playerone.databinding.AudioItemBinding
import com.example.playerone.fragments.audio.models.AudioModel


class AudioAdapter(
    private val context: Context,
    private val onAudioClicked: (AudioModel) -> Unit,
    private val audioMenuclicked: (AudioModel) -> Unit
):RecyclerView.Adapter<AudioAdapter.AudioViewHolder>() {

    private var audioList: ArrayList<AudioModel>? = null

    fun setVideoList(audioList: ArrayList<AudioModel>){
        this.audioList = audioList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AudioViewHolder {
        val view = AudioItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AudioViewHolder(view)
    }

    override fun onBindViewHolder(holder: AudioViewHolder, position: Int) {
        holder.binding.audioTitle.text = audioList?.get(position)?.title

        Glide.with(context)
            .load(audioList?.get(position)?.artUri)
            .apply(RequestOptions().placeholder(R.drawable.ic_baseline_play_circle_24).centerCrop())
            .into(holder.binding.audioThumbnail)

        holder.binding.root.setOnClickListener {
            audioList?.get(position)?.let { it1 -> onAudioClicked(it1) }
        }

        holder.binding.btnVerticalMenu.setOnClickListener {
            audioList?.get(position)?.let { it1 -> audioMenuclicked(it1) }
        }



    }

    override fun getItemCount(): Int {
        return if (audioList == null) 0
        else audioList?.size!!
    }


    class AudioViewHolder(val binding: AudioItemBinding):RecyclerView.ViewHolder(binding.root){}

}