package com.example.playerone.fragments.images.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.playerone.databinding.ImageItemBinding
import com.example.playerone.fragments.images.models.ImageModel
import com.example.playerone.fragments.images.models.ImageModelItem

class ImagesAdapter(private val context: Context,private val onImageItemClicked: OnImageItemClicked):RecyclerView.Adapter<ImagesAdapter.ImagesViewHolder>() {


    private var imageResponse:ImageModel? = null

    fun setImageResponse(imageResponse:ImageModel){
        this.imageResponse = imageResponse
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val view = ImageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ImagesViewHolder(view)

    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {
        Glide.with(context).asBitmap().load(imageResponse?.get(position)?.download_url).into(holder.binding.imageView)
        holder.binding.root.setOnClickListener {
            imageResponse?.let { it1 -> onImageItemClicked.onClick(it1[position]) }
        }
    }

    override fun getItemCount(): Int {
        return if (imageResponse == null) 0
        else imageResponse?.size!!
    }

    class ImagesViewHolder(val binding:ImageItemBinding): RecyclerView.ViewHolder(binding.root) {}


    interface OnImageItemClicked{
        fun onClick(imageModelItem: ImageModelItem)
        fun onLongClick()
    }
}