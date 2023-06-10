package com.example.playerone.fragments.video_folder.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playerone.databinding.FolderItemBinding
import com.example.playerone.databinding.VideoItemBinding
import com.example.playerone.fragments.video.models.VideoModel
import com.example.playerone.fragments.video_folder.models.VideoFolderModel


class VideoFolderAdapter(private val context: Context, private val videoItemClick: VideoItemClick): RecyclerView.Adapter<VideoFolderAdapter.VideoViewModel>() {

    private var videoList: ArrayList<VideoFolderModel>? = null

    fun setVideoList(videoList: ArrayList<VideoFolderModel>){
        this.videoList = videoList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewModel {
        val view = FolderItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return VideoViewModel(view)

    }

    override fun onBindViewHolder(holder: VideoViewModel, position: Int) {
        holder.binding.folderTitle.text = videoList?.get(position)?.folderName

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


    class VideoViewModel(val binding: FolderItemBinding): RecyclerView.ViewHolder(binding.root){

    }


    interface VideoItemClick{

        fun onClick(videoFolderModel:VideoFolderModel)
        fun onBtnClicked(position: Int){}
        fun onLongClick(position: Int){}

    }

}