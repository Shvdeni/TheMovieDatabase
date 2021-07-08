package com.example.themoviedatabase

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.Volley
import com.example.themoviedatabase.models.MovieModel


internal class MoviesAdapter(private var moviesList: List<MovieModel>, contexte: Context) :
    RecyclerView.Adapter<MoviesAdapter.MyViewHolder>() {

    var contexte = contexte;
    private var clickListener: ClickListener? = null


    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var blog_image: ImageView = view.findViewById(R.id.blog_image)
        var title: TextView = view.findViewById(R.id.title)
        var vote_average: TextView = view.findViewById(R.id.vote_average)

        init {
            if (clickListener != null) {
                itemView.setOnClickListener(this)
            }
        }

        fun bindItems(data: String) {
            title.text = data
        }

        override fun onClick(v: View?) {
            if (v != null) {
                clickListener?.onItemClick(v,adapterPosition)
            }
        }
    }
    @NonNull
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_list_item, parent, false)
        return MyViewHolder(itemView)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = moviesList[position]

        val queue = Volley.newRequestQueue(contexte)
//        val imageUrl = "https://image.tmdb.org/t/p/w300/jTswp6KyDYKtvC52GbHagrZbGvD.jpg"
        val imageUrl = "https://image.tmdb.org/t/p/w300" + movie.getposter_path()

        // request a image response from the provided url
        val imageRequest = ImageRequest(
            imageUrl,
            {bitmap -> // response listener
//                imageView.setImageBitmap(bitmap)
                holder.blog_image.setImageBitmap(bitmap)
            },
            0, // max width
            0, // max height
            ImageView.ScaleType.CENTER_CROP, // image scale type
            Bitmap.Config.ARGB_8888, // decode config
            {error-> // error listener
            }
        )
        queue.add(imageRequest)

        holder.title.text = movie.getTitle()
        holder.vote_average.text = movie.getvote_average()
    }

    fun getItem(position: Int): String? {
        //return if (mDataSet != null) mDataSet[position] else null
//        return moviesList?.get(position)
        return moviesList?.get(position).getId().toString()
    }

    fun getItemPoster_path(position: Int): String? {
        return moviesList?.get(position).getposter_path()
    }

    fun setOnItemClickListener(clickListener: ClickListener) {
        this.clickListener = clickListener
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    interface ClickListener {
        fun onItemClick(v: View,position: Int)
    }
}