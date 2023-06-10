package com.example.playerone.fragments.video.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playerone.databinding.VideoItemBinding
import com.example.playerone.fragments.video.models.VideoModel
import com.example.playerone.R
import com.example.playerone.fragments.images.models.ImageModel


class VideoAdapter(private val context: Context, private val videoItemClick: VideoItemClick): RecyclerView.Adapter<VideoAdapter.VideoViewModel>() {

    private var videoList: ArrayList<VideoModel>? = null

    fun setVideoList(videoList: ArrayList<VideoModel>){
        this.videoList = videoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewModel {
        val view = VideoItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VideoViewModel(view)

    }

    override fun onBindViewHolder(holder: VideoViewModel, position: Int) {
        holder.binding.videoTitle.text = videoList?.get(position)?.title
        Glide.with(context).asBitmap().load(videoList?.get(position)?.artUri).into(holder.binding.imageView)
        holder.binding.root.setOnClickListener {
            videoList?.get(position)?.let { it1 -> videoItemClick.onClick(it1) }
        }
        holder.binding.btnVerticalMenu.setOnClickListener {
            videoItemClick.onBtnClicked(position)
        }
    }

    override fun getItemCount(): Int {
        return if (videoList == null) 0
        else videoList?.size!!
    }


    class VideoViewModel(val binding: VideoItemBinding): RecyclerView.ViewHolder(binding.root){

    }


    interface VideoItemClick{

        fun onClick(videoModel: VideoModel)
        fun onBtnClicked(position: Int)
        fun onLongClick(position: Int)

    }

}