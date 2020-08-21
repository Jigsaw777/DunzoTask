package com.example.dunzotask.ui.viewholders

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dunzotask.R
import com.example.dunzotask.domain.entities.PhotoEntity
import kotlinx.android.synthetic.main.search_item_view_layout.view.*

class SearchItemVH(private val view: View) : RecyclerView.ViewHolder(view) {

    fun bindData(photoEntity: PhotoEntity) {
        view.search_item_title.text = photoEntity.title
        val imageUrl =
            "https://farm${photoEntity.farm}.staticflickr.com/${photoEntity.server}/${photoEntity.id}_${photoEntity.secret}_m.jpg"
        Glide.with(view.context)
            .load(imageUrl)
            .placeholder(R.drawable.ic_baseline_cloud_download_24)
            .into(view.search_item_image)
    }
}