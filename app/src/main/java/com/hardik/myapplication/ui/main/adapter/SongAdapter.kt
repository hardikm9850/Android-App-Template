package com.hardik.myapplication.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hardik.myapplication.R
import com.hardik.myapplication.database.SongsEntity
import kotlinx.android.synthetic.main.adapter_song_item.view.*

class SongAdapter(private val songsList: List<SongsEntity>, private val clickListener: (SongsEntity) -> Unit) : RecyclerView.Adapter<SongAdapter.SongsItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongsItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_song_item, parent, false)
        return SongsItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: SongsItemViewHolder, position: Int) {
        holder.bind(songsList[position])
    }

    override fun getItemCount() = songsList.size

    inner class SongsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(songsEntity: SongsEntity) {
            itemView.tvSongName.text = songsEntity.name
            itemView.tvArtist.text = songsEntity.artist
            Glide.with(itemView.context)
                    .load(songsEntity.imageUrl)
                    .into(itemView.imgSongAvatar)
            itemView.setOnClickListener {
                clickListener.invoke(songsEntity)
            }
        }

    }
}