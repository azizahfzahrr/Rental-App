package com.example.cakratech.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.cakratech.R

class BannerAdapter(private val imageResIds: List<Int>) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_banner, parent, false)
        return BannerViewHolder(view)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val imageResId = imageResIds[position]
        holder.bannerImage.setImageResource(imageResId)
    }

    override fun getItemCount(): Int = imageResIds.size

    class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bannerImage: ImageView = view.findViewById(R.id.bannerImage)
    }
}

