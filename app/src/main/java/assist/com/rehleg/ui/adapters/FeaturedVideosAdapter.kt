package com.assist.lego.testing.ui.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import assist.com.rehleg.R
import assist.com.rehleg.ui.utils.inflate


/**
 * Created by Sergiu on 28.10.2017.
 */
class FeaturedVideosAdapter(private val context: Context) : RecyclerView.Adapter<FeaturedVideosAdapter.VideoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): VideoViewHolder = VideoViewHolder(parent!!.inflate(R.layout.featured_video_item))

    override fun onBindViewHolder(holder: VideoViewHolder?, position: Int) = holder!!.bind(position)

    override fun getItemCount(): Int = 5

    override fun getItemId(position: Int): Long = position.toLong()


    class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(position: Int) {

        }

    }

}