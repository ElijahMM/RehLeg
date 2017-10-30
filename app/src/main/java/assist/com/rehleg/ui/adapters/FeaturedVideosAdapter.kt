package com.assist.lego.testing.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R

/**
 * Created by Sergiu on 28.10.2017.
 */
class FeaturedVideosAdapter(private val context: Context) : RecyclerView.Adapter<FeaturedVideosAdapter.VideoViewHolder>() {

    override fun onBindViewHolder(holder: VideoViewHolder?, position: Int) = holder!!.bind()

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VideoViewHolder =
            VideoViewHolder(LayoutInflater.from(context).inflate(R.layout.featured_video_item, parent, false))

    override fun getItemCount(): Int = 5


    class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {

        }

    }

}